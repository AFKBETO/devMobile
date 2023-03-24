package tools;

import android.content.Context;
import java.util.Map;

public class DataStorage {
    public static void saveData(Context context, String key, String value) {
        context.getSharedPreferences("data", Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }
    public static Map<String, ?> loadData(Context context) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE).getAll();
    }
}
