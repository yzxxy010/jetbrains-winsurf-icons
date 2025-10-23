# 请将下面的URL替换为你的GitHub仓库地址
$REPO_URL = "https://github.com/yzxxy010/jetbrains-winsurf-icons.git"

Write-Host "准备推送到GitHub..." -ForegroundColor Green

# 添加远程仓库
git remote add origin $REPO_URL

# 设置主分支为main
git branch -M main

# 推送到GitHub
git push -u origin main

Write-Host "推送完成！" -ForegroundColor Green
Write-Host "访问你的仓库: $REPO_URL" -ForegroundColor Cyan
