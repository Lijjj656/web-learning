param([string]$BaseUrl = 'http://localhost:8084')
$ErrorActionPreference = 'Stop'
$ProgressPreference = 'SilentlyContinue'
$script:Checks = 0

function Assert-Check([bool]$Condition, [string]$Label) {
    if (-not $Condition) { throw "FAIL: $Label" }
    $script:Checks++
    Write-Output "PASS: $Label"
}

function Call-Api([string]$Method, [string]$Path, $Body = $null, [string]$Token = '', [int]$Expected = 200) {
    $request = @{ Uri = "$BaseUrl$Path"; Method = $Method; UseBasicParsing = $true; TimeoutSec = 15 }
    if ($Token) { $request.Headers = @{ Authorization = "Bearer $Token" } }
    if ($null -ne $Body) {
        $request.ContentType = 'application/json; charset=utf-8'
        $request.Body = [Text.Encoding]::UTF8.GetBytes(($Body | ConvertTo-Json -Compress))
    }
    try {
        $response = Invoke-WebRequest @request
        $status = [int]$response.StatusCode
        $content = [Text.Encoding]::UTF8.GetString($response.RawContentStream.ToArray())
    } catch {
        $response = $_.Exception.Response
        if ($null -eq $response) { throw }
        $status = [int]$response.StatusCode
        $reader = New-Object IO.StreamReader($response.GetResponseStream())
        try { $content = $reader.ReadToEnd() } finally { $reader.Dispose() }
    }
    if ($status -ne $Expected) { throw "$Method $Path expected $Expected, got $status. $content" }
    $data = if ($content) { ConvertFrom-Json -InputObject $content } else { $null }
    return [pscustomobject]@{ Status = $status; Data = $data }
}

$labels = ConvertFrom-Json '{"priority":"\u666e\u901a","notStarted":"\u672a\u5f00\u59cb","completed":"\u5df2\u5b8c\u6210"}'
$runId = [Guid]::NewGuid().ToString('N')
$emailA = "smoke-a-$runId@example.com"
$emailB = "smoke-b-$runId@example.com"
$password = 'Test-' + [Guid]::NewGuid().ToString('N')
$tokenA = ''
$taskId = $null
try {
    $a = Call-Api POST /api/auth/register @{ name = 'Smoke A'; email = $emailA; password = $password }
    $b = Call-Api POST /api/auth/register @{ name = 'Smoke B'; email = $emailB; password = $password }
    $tokenA = $a.Data.token
    $tokenB = $b.Data.token
    Assert-Check ([bool]$tokenA -and [bool]$tokenB) 'Register two users'
    $login = Call-Api POST /api/auth/login @{ email = $emailA; password = $password }
    $tokenA = $login.Data.token
    Assert-Check ([bool]$tokenA) 'Login returns token'
    $wrong = Call-Api POST /api/auth/login @{ email = $emailA; password = 'WrongPassword123' } '' 401
    Assert-Check ([bool]$wrong.Data.message) 'Wrong password returns 401 and message'
    $profile = Call-Api GET /api/auth/me $null $tokenA
    Assert-Check ($profile.Data.email -eq $emailA -and $null -eq $profile.Data.passwordHash) 'Profile excludes password hash'
    foreach ($path in @('/api/auth/me', '/api/tasks', '/api/tasks/stats')) {
        $missing = Call-Api GET $path $null '' 401
        Assert-Check ([bool]$missing.Data.message) "Missing token returns 401: $path"
    }
    $invalid = Call-Api GET /api/tasks $null 'invalid-token' 401
    Assert-Check ([bool]$invalid.Data.message) 'Invalid token rejected'
    $parts = $tokenA.Split('.')
    $tampered = $parts[0] + '.' + $parts[1] + '.' + ('A' * $parts[2].Length)
    $null = Call-Api GET /api/tasks $null $tampered 401
    Assert-Check $true 'Tampered signature rejected'
    $empty = Call-Api GET /api/tasks $null $tokenA
    Assert-Check (@($empty.Data).Count -eq 0) 'New user has no tasks'
    $body = @{ title = 'Smoke task'; category = 'Web'; priority = $labels.priority; status = $labels.notStarted; userId = $profile.Data.id + 100000 }
    $blank = $body.Clone(); $blank.title = '   '
    $null = Call-Api POST /api/tasks $blank $tokenA 400
    Assert-Check $true 'Blank title rejected'
    $illegal = $body.Clone(); $illegal.status = 'invalid'
    $null = Call-Api POST /api/tasks $illegal $tokenA 400
    Assert-Check $true 'Unknown status rejected'
    $created = Call-Api POST /api/tasks $body $tokenA 201
    $taskId = $created.Data.id
    Assert-Check ($taskId -gt 0 -and $created.Data.title -eq $body.title) 'Create task returns persisted id'
    $list = Call-Api GET /api/tasks $null $tokenA
    Assert-Check (@($list.Data).Count -eq 1 -and $list.Data[0].id -eq $taskId) 'Task is visible to owner; client userId ignored'
    $stats = Call-Api GET /api/tasks/stats $null $tokenA
    Assert-Check ($stats.Data.total -eq 1 -and $stats.Data.notStarted -eq 1) 'Statistics count new task'
    $other = Call-Api GET /api/tasks $null $tokenB
    Assert-Check (@($other.Data).Count -eq 0) 'Second user cannot list first user tasks'
    $null = Call-Api PUT "/api/tasks/$taskId" $body $tokenB 404
    $null = Call-Api DELETE "/api/tasks/$taskId" $null $tokenB 404
    Assert-Check $true 'Cross-user update and delete both return 404'
    $body.title = 'Updated smoke task'; $body.status = $labels.completed
    $updated = Call-Api PUT "/api/tasks/$taskId" $body $tokenA
    Assert-Check ($updated.Data.title -eq $body.title -and $updated.Data.status -eq $labels.completed) 'Edit title and status'
    $null = Call-Api PUT "/api/tasks/$taskId" $body $tokenA
    Assert-Check $true 'Saving unchanged values succeeds'
    $stats = Call-Api GET /api/tasks/stats $null $tokenA
    Assert-Check ($stats.Data.total -eq 1 -and $stats.Data.completed -eq 1 -and $stats.Data.notStarted -eq 0) 'Statistics follow status update'
    $otherStats = Call-Api GET /api/tasks/stats $null $tokenB
    Assert-Check ($otherStats.Data.total -eq 0) 'Statistics isolated by user'
    $null = Call-Api DELETE "/api/tasks/$taskId" $null $tokenA 204
    $deletedId = $taskId; $taskId = $null
    $null = Call-Api DELETE "/api/tasks/$deletedId" $null $tokenA 404
    Assert-Check $true 'Delete returns 204; repeat delete returns 404'
    $stats = Call-Api GET /api/tasks/stats $null $tokenA
    Assert-Check ($stats.Data.total -eq 0 -and $stats.Data.completed -eq 0) 'Statistics follow deletion'
    Write-Output "All $script:Checks checks passed. Test tasks removed."
} finally {
    if ($null -ne $taskId -and $tokenA) {
        try { $null = Call-Api DELETE "/api/tasks/$taskId" $null $tokenA 204 } catch { Write-Warning 'Test task cleanup failed.' }
    }
    Write-Output "Test accounts: $emailA, $emailB"
}
