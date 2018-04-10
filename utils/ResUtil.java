




/** 
* 反射获取资源文件 
* @version V1.0 
* @author: SUNTAO 
* @date: 2017-01-11 16:27 
*/ 
public class ResUtil {
public static int getLayoutId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam, "layout",context.getPackageName());
}

public static int getStringId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam, "string",context.getPackageName());
}

public static int getMipmapId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam,"mipmap", context.getPackageName());
}

public static int getDrawableId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam,"drawable", context.getPackageName());
}

public static int getStyleId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam,"style", context.getPackageName());
}

public static int getId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam,"id", context.getPackageName());
}

public static int getColorId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam,"color", context.getPackageName());
}
public static int getArrayId(Context context, String cparam) {
    return context.getResources().getIdentifier(cparam,"array", context.getPackageName());
}
}