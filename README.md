``` java
public record Animal(
        String name,
        Type type,
        Sex sex,
        int age,
        int height,
        int weight,
        boolean bites
) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }
}
```

###Задача 1
Отсортировать животных по росту от самого маленького к самому большому ->`List<Animal>`

###Задача 2
Отсортировать животных по весу от самого тяжелого к самому легкому, выбрать k первых -> `List<Animal>`

###Задача 3
Сколько животных каждого вида -> `Map<Animal.Type, Integer>`

###Задача 4
У какого животного самое длинное имя -> `Animal`

###Задача 5
Каких животных больше: самцов или самок -> `Sex`

###Задача 6
Самое тяжелое животное каждого вида -> `Map<Animal.Type, Animal>`

###Задача 7
K-е самое старое животное -> `Animal`

###Задача 8
Самое тяжелое животное среди животных ниже k см -> `Optional<Animal>`

###Задача 9
Сколько в сумме лап у животных в списке -> `Integer`

###Задача 10
Список животных, возраст у которых не совпадает с количеством лап -> `List<Animal>`

###Задача 11
Список животных, которые могут укусить (bites == true) и рост которых превышает 100 см -> `List<Animal>`

###Задача 12
Сколько в списке животных, вес которых превышает рост -> `Integer`

###Задача 13
Список животных, имена которых состоят из более чем двух слов -> `List<Animal>`

###Задача 14
Есть ли в списке собака ростом более k см -> `Boolean`

###Задача 15
Найти суммарный вес животных каждого вида, которым от k до l лет -> `Map<Animal.Type, Integer>`

###Задача 16
Список животных, отсортированный по виду, затем по полу, затем по имени -> `List<Animal>`

###Задача 17
Правда ли, что пауки кусаются чаще, чем собаки -> `Boolean` (если данных для ответа недостаточно, вернуть false)

###Задача 18
Найти самую тяжелую рыбку в 2-х или более списках -> `Animal`

###Задача 19
Животные, в записях о которых есть ошибки: вернуть имя и список ошибок -> `Map<String, Set<ValidationError>>`.

Класс `ValidationError` и набор потенциальных проверок нужно придумать самостоятельно.

###Задача 20
Сделать результат предыдущего задания более читабельным: вернуть имя и названия полей с ошибками, объединенные в строку -> `Map<String, String>`
