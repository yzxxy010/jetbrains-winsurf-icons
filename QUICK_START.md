# 快速开始指南

## 构建插件

1. **下载并初始化Gradle Wrapper**（首次构建时）
```powershell
# 在项目根目录执行
.\gradlew.bat wrapper
```

2. **构建插件**
```powershell
.\gradlew.bat buildPlugin
```

构建成功后，插件zip文件将生成在 `build\distributions\` 目录中。

## 安装到Rider 2025

1. 打开Rider
2. 进入 **文件** → **设置** （或按 `Ctrl+Alt+S`）
3. 选择左侧的 **插件**
4. 点击右上角的齿轮图标 ⚙️
5. 选择 **从磁盘安装插件...**
6. 导航到 `jetbrains-icon-theme-plugin\build\distributions\`
7. 选择生成的 `winsurf-icon-theme-1.0.0.zip` 文件
8. 点击 **确定** 安装
9. **重启Rider** 以应用更改

## 验证安装

重启后：
1. 打开任何项目
2. 在项目视图中查看文件和文件夹图标是否已更改
3. 可以在 **设置** → **外观与行为** → **Winsurf Icons** 中调整设置

## 故障排除

如果图标未显示：
1. 确保插件已启用（设置 → 插件 → 已安装）
2. 尝试使缓存无效并重启（文件 → 使缓存无效并重启）
3. 检查IDE日志是否有错误信息（帮助 → 显示日志）

## 开发模式运行

如果你想在开发模式下测试插件：
```powershell
.\gradlew.bat runIde
```
这将启动一个带有插件的新IDE实例。
