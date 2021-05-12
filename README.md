# 伯约地产
# AndroidPicker不混淆
#-keepattributes InnerClasses,Signature
#-keepattributes *Annotation*

#-keep class cn.qqtheme.framework.entity.** { *;}

# 百度地图不混淆
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.baidu.vi.** {*;}
-dontwarn com.baidu.**