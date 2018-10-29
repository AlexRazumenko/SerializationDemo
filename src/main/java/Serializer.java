import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Serializer {

    public static String serialize(String path, City city) {

        StringBuilder toSerialize = new StringBuilder("");
        Class<?> cls = city.getClass();

        try {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    field.setAccessible(true);
                    toSerialize.append(field.getName() + ": " + field.get(city) + " ;");
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path));
                    outputStream.writeObject(toSerialize);
                }
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return toSerialize.toString();
    }

    public static String deserialize(String path) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
        City city = new City();
        String toDeserialize = inputStream.readObject().toString();
        String[] fieldsInfo = toDeserialize.split(";");
        for (String str : fieldsInfo) {
            String[] fieldInfo = str.split(":");
            String name = fieldInfo[0];
            String value = fieldInfo[1];

            Field field = city.getClass().getDeclaredField(name);
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            if (field.getType() == int.class) {
                field.setInt(city, Integer.parseInt(value.trim()));
            } else if (field.getType() == String.class) {
                field.set(city, value.trim());
            } else if (field.getType() == long.class) {
                field.setLong(city, Long.parseLong(value.trim()));
            }

        }
        String cityString = "name: " + city.getName() + ", Population: " + city.getPopulation();
        return cityString;
    }
}



