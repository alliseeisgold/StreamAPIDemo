import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // Отсортировать животных по росту от самого маленького к самому большому -> List<Animal>
    static List<Animal> task1(final Animal[] animals) {
        return Stream.of(animals)
                .sorted(Comparator.comparingInt(Animal::height))
                .toList();
    }

    // Отсортировать животных по весу от самого тяжелого к самому легкому, выбрать k первых -> List<Animal>
    static List<Animal> task2(final Animal[] animals, int k) {
        return Stream.of(animals)
                .sorted(Comparator.comparingInt(Animal::weight).reversed())
                .limit(Math.min(k, animals.length))
                .toList();
    }

    // Сколько животных каждого вида -> Map<Animal.Type, Long>
    static Map<Animal.Type, Long> task3(final Animal[] animals) {
        return Stream.of(animals).collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    // У какого животного самое длинное имя -> Animal
    static Optional<Animal> task4(final Animal[] animals) {
        return Stream.of(animals).max(Comparator.comparingInt(animal -> animal.name().length()));
    }

    // Каких животных больше: самцов или самок -> Sex
    static Animal.Sex task5(final Animal[] animals) {
        var map = Stream.of(animals)
                .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));
        return map.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    // Самое тяжелое животное каждого вида -> Map<Animal.Type, Animal>
    static Map<Animal.Type, Animal> task6(final Animal[] animals) {
        return Stream.of(animals)
                .collect(Collectors.toMap(
                        Animal::type,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparingInt(Animal::weight))
                ));
    }

    // K-е самое старое животное -> Animal
    static Optional<Animal> task7(final Animal[] animals, int k) {
        return Stream.of(animals).sorted(Comparator.comparingInt(Animal::age).reversed()).skip(k - 1).findFirst();
    }

    // Самое тяжелое животное среди животных ниже k см -> Optional<Animal>
    static Optional<Animal> task8(final Animal[] animals, int k) {
        return Stream.of(animals)
                .filter(animal -> animal.height() < k)
                .max(Comparator.comparingInt(Animal::weight));
    }


    // Сколько в сумме лап у животных в списке -> Integer
    static Integer task9(final Animal[] animals) {
        return Stream.of(animals).mapToInt(Animal::paws).sum();
    }


    // Список животных, возраст у которых не совпадает с количеством лап -> List<Animal>
    static List<Animal> task10(final Animal[] animals) {
        return Stream.of(animals)
                .filter(animal -> animal.age() != animal.paws())
                .toList();
    }

    // Список животных, которые могут укусить (bites == true) и рост которых превышает 100 см -> List<Animal>
    static List<Animal> task11(final Animal[] animals) {
        return Stream.of(animals)
                .filter(animal -> animal.bites() && animal.height() > 100)
                .toList();
    }

    // Сколько в списке животных, вес которых превышает рост -> Integer
    static Integer task12(final Animal[] animals) {
        return Math.toIntExact(Stream.of(animals)
                .filter(animal -> animal.height() < animal.weight())
                .count());
    }


    // Список животных, имена которых состоят из более чем двух слов -> List<Animal>
    static List<Animal> task13(final Animal[] animals) {
        return Stream.of(animals)
                .filter(animal -> animal.name().split("\\s+").length > 2)
                .toList();
    }

    // Есть ли в списке собака ростом более k см -> Boolean
    static Boolean task14(final Animal[] animals, int k) {
        return Stream.of(animals).anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    // Найти суммарный вес животных каждого вида, которым от k до l лет -> Map<Animal.Type, Integer>
    static Map<Animal.Type, Integer> task15(final Animal[] animals, int k, int l) {
        return Stream.of(animals)
                .filter(animal -> animal.age() >= k && animal.age() <= l)
                .collect(Collectors.groupingBy(Animal::type,
                        Collectors.mapping(Animal::weight, Collectors.summingInt(Integer::intValue))));
    }

    // Список животных, отсортированный по виду, затем по полу, затем по имени -> List<Animal>
    static List<Animal> task16(final Animal[] animals) {
        return Stream.of(animals)
                .sorted(Comparator.comparing(Animal::type)
                        .thenComparing(Animal::sex)
                        .thenComparing(Animal::name)
                ).toList();
    }

    // Правда ли, что пауки кусаются чаще, чем собаки -> Boolean (если данных для ответа недостаточно, вернуть false)
    static Boolean task17(final Animal[] animals) {
        long dogBitesSum = Stream.of(animals)
                .filter(animal -> animal.type() == Animal.Type.DOG && animal.bites()).count();

        long spiderBitesSum = Stream.of(animals)
                .filter(animal -> animal.type() == Animal.Type.SPIDER && animal.bites()).count();
        return dogBitesSum > spiderBitesSum;
    }

    // Найти самую тяжелую рыбку в 2-х или более списках -> Animal
    static Animal task18(final Animal[][] animals) {
        return Stream.of(animals)
                .flatMap(Arrays::stream)
                .filter(a -> a.type() == Animal.Type.FISH)
                .max(Comparator.comparingInt(Animal::weight))
                .orElse(null);
    }

    // Животные, в записях о которых есть ошибки: вернуть имя и список ошибок -> Map<String, Set<ValidationError>>.
    static Map<String, Set<ValidationError>> task19(final Animal[] animals) {
        return Stream.of(animals)
                .filter(Main::hasErrors)
                .collect(Collectors.toMap(
                        Animal::name,
                        Main::getValidationErrors
                ));
    }

    static boolean hasErrors(Animal animal) {
        return !getValidationErrors(animal).isEmpty();
    }


    record ValidationError(String message) {
    }

    static Set<ValidationError> getValidationErrors(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();

        if (animal.age() < 0) {
            errors.add(new ValidationError("Age cannot be negative"));
        }
        if (animal.weight() < 0) {
            errors.add(new ValidationError("Weight cannot be negative"));
        }
        if (animal.height() < 0) {
            errors.add(new ValidationError("Height cannot be negative"));
        }
        return errors;
    }


    // Сделать результат предыдущего задания более читабельным: вернуть имя и названия полей с ошибками, объединенные в строку -> Map<String, String>
    static Map<String, String> task20(final Animal[] animals) {
        return Stream.of(animals)
                .filter(Main::hasErrors)
                .collect(Collectors.toMap(
                        Animal::name,
                        Main::getErrorFields
                ));
    }

    static String getErrorFields(Animal animal) {
        Set<ValidationError> errors = getValidationErrors(animal);
        return errors.stream()
                .map(ValidationError::message)
                .collect(Collectors.joining(", "));
    }

    public static void main(String[] args) {
        Animal[] animals = new Animal[]{
                new Animal("Мурзик Ba Ba", Animal.Type.CAT, Animal.Sex.M, 4, 20, 5, false),
                new Animal("Бобик", Animal.Type.DOG, Animal.Sex.M, 5, 40, 15, false),
                new Animal("Кеша", Animal.Type.BIRD, Animal.Sex.M, 2, 10, 1, false),
                new Animal("Голди", Animal.Type.FISH, Animal.Sex.F, 1, 5, 1, false),
                new Animal("Шарик", Animal.Type.DOG, Animal.Sex.M, 4, 35, 12, true),
                new Animal("Пушок", Animal.Type.CAT, Animal.Sex.F, 2, 15, 3, false),
                new Animal("Паучок", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true),
                new Animal("Киса", Animal.Type.CAT, Animal.Sex.F, 6, 25, 46, false),
                new Animal("Дружок", Animal.Type.DOG, Animal.Sex.M, 3, 300, 10, true),
                new Animal("Птичка", Animal.Type.BIRD, Animal.Sex.F, 4, 15, 2, false)
        };

        Animal[] fish = new Animal[]{
                new Animal("Roger", Animal.Type.FISH, Animal.Sex.F, 1, 5, 10, false),
        };
        // task1(animals).forEach(System.out::println);
        // task2(animals, 6).forEach(System.out::println);
        // task3(animals).forEach((key, value) -> System.out.println(key + " " + value));
        // System.out.println(task4(animals));
        // System.out.println(task5(animals));
        // task6(animals).forEach((key, value) -> System.out.println(key + ": " + value));
        // System.out.println(task7(animals, 3));
        // System.out.println(task8(animals, 20));
        // System.out.println(task9(animals));
        // task10(animals).forEach(System.out::println);
        // task11(animals).forEach(System.out::println);
        // System.out.println(task12(animals));
        // task13(animals).forEach(System.out::println);
        // System.out.println(task14(animals, 15));
        // task15(animals, 1, 6).forEach((key, value) -> System.out.println(key + ": " + value));
        // task16(animals).forEach(System.out::println);
        // System.out.println(task17(animals));
        // System.out.println(task18(new Animal[][]{animals, fish}));
    }
}
