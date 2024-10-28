import java.io.*;
import java.util.Objects;

public class AnimalDeserializer {
    public static Animal[] deserializeAnimalArray(byte[] data) {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {

            Animal[] Animal = new Animal[ois.readInt()];

            for (int i = 0; i < Animal.length; i++)
                Animal[i] = (Animal) ois.readObject();


            return Animal;
        } catch (Throwable e) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws IOException {
        Animal cat = new Animal("cat");
        Animal dog = new Animal("dog");
        Animal tiger = new Animal("tiger");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeInt(3); // Запись размера массива
        oos.writeObject(cat);
        oos.writeObject(dog);
        oos.writeObject(tiger);
        oos.flush();
        byte[] serializedData = baos.toByteArray();
            
        try {
            Animal[] animals = AnimalDeserializer.deserializeAnimalArray(serializedData);
            for (Animal animal : animals) {
                System.out.println(animal.name);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка десериализации: " + e.getMessage());
        }
    }

    static class Animal implements Serializable {
        final String name;

        public Animal(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Animal) {
                return Objects.equals(name, ((Animal) obj).name);
            }
            return false;
        }
    }
}
