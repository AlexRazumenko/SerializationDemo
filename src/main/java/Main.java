public class Main {

    public static void main(String[] args) {

        String path = "e:\\ser.txt";
        City city = new City("Odesa", 1000000);

        try {
            String s = ("Serialized:\n " + Serializer.serialize(path, city) + "\n" +
                    "Deserialized:\n " + Serializer.deserialize(path));
            System.out.println(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
