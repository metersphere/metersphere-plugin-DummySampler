# MeterSphere 接口测试并行控制器插件

[MeterSphere](https://github.com/metersphere/metersphere) 是一站式开源持续测试平台，涵盖测试跟踪、接口测试、性能测试、团队协作等功能，兼容JMeter 等开源标准，有效助力开发和测试团队充分利用云弹性进行高度可扩展的自动化测试，加速高质量软件的交付。

该项目为 MeterSphere 配套的 DummySampler 插件，在 MeterSphere 中安装该插件后可在接口自动化场景中添加模拟请求，该步骤可以按照用户指定的请求内容及响应结果进行返回，效果与 [JMeter 的 DummySampler](https://jmeter-plugins.org/wiki/DummySampler/) 插件相同。

## 安装使用

1. 在仓库的 Releases 页面下载最新的插件 Jar 包
2. 在 MeterSphere `系统设置`-`插件管理` 页面上传下载的插件
3. 在接口自动化场景中添加 `模拟请求` 步骤

## 问题反馈

如果您在使用过程中遇到什么问题，或有进一步的需求需要反馈，请提交 GitHub Issue 到 [MeterSphere 项目的主仓库](https://github.com/metersphere/metersphere/issues)
