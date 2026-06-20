public satatic void check InstallPermission(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        boolean canInstall = context.getPackageManager().canRequestPackageInstalls();
        if (!canInstall) {
            // 申请安装未知来源应用的权限
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                Uri.parse("package:" + context.getPackageName()));
                
            context.startActivity(intent);
        }
    }
}