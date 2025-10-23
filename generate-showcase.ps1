# PowerShell script to generate showcase images
Write-Host "生成宣传图..." -ForegroundColor Green

# 创建输出目录
$outputDir = "showcase"
if (!(Test-Path $outputDir)) {
    New-Item -ItemType Directory -Path $outputDir | Out-Null
}

Write-Host "`n请按以下步骤生成宣传图：" -ForegroundColor Yellow
Write-Host "================================================" -ForegroundColor Cyan

Write-Host "`n步骤 1: 打开展示页面" -ForegroundColor Green
Write-Host "  打开文件: showcase.html"
Write-Host "  (双击文件或在浏览器中打开)"

Write-Host "`n步骤 2: 截取宣传图" -ForegroundColor Green
Write-Host "  推荐使用以下工具截图："
Write-Host "  - Windows: Win + Shift + S (截图工具)"
Write-Host "  - 浏览器: F12 开发者工具 > Ctrl+Shift+P > Capture screenshot"
Write-Host "  - 第三方工具: Snagit, ShareX 等"

Write-Host "`n步骤 3: 保存截图" -ForegroundColor Green
Write-Host "  保存到 showcase 文件夹，建议命名："
Write-Host "  - showcase-full.png (完整展示)"
Write-Host "  - showcase-files.png (文件图标部分)"
Write-Host "  - showcase-folders.png (文件夹图标部分)"
Write-Host "  - showcase-header.png (顶部横幅)"

Write-Host "`n================================================" -ForegroundColor Cyan
Write-Host "提示：" -ForegroundColor Yellow
Write-Host "- 浏览器窗口建议调整为 1920x1080 或更高分辨率"
Write-Host "- 使用 Chrome/Edge 浏览器效果最佳"
Write-Host "- 截图时确保页面完全加载"
Write-Host ""

# 打开showcase.html
Start-Process "showcase.html"

Write-Host "已在浏览器中打开展示页面！" -ForegroundColor Green
