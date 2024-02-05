# FileContentFilter
(RU)
Эта утилита предназначена для разделения строк, целых чисел и чисел с плавающей точкой в файлах. В качестве разделителя используется перевод строки.
Для запуска утилиты нужно использовать файл FileContentFilter.jar находящийся в out/artifacts/FileContentFilter_jar директории данного репозитория. Файл необходимо скачать и запустить с помощь команды:

java -jar FileContentFilter.jar [-o path] [-p prefix] [-a] [-f|-s] file_name [file_name_1 file_name_2 ..]

где FileContentFilter.jar - название утилиты,  file_name [file_name_1 file_name_2 ..] - название вводимого[ых] файла[ов]. Для запуска программы на вашем компьютере должна быть установлена Java 18 (62) версии и выше.
Остальные параметры являются вспомогательными и необязательны к использованию.
Программу можно самостоятельно запустить с помощью IDE, поддерживающей Java версии 18 (62) и выше. Ввод параметров программы определяется IDE и может быть индивидуален.
Утилита всегда генерирует три выходных файла integers.txt - для целых числе, floats.txt - для чисел с плавающей точкой, strings.txt - для строк.

Необязательные параметры

-o path - задаёт путь path выходных файлам.
-p prefix - задаёт префикс prefix к выходным файлам. Например, -p sample_, следовательно выходные файлы будут называться sample_integers.txt, sample_floats.txt, sample_strings.txt.
-a - включает режим дозаписи в файл. По умолчанию все выходные файлы перезаписываются новым содержимым.
-s - вывод количества записанных элементов в каждый выходной файл.
-f - вывод расширенной статистики. Минимальное, максимальное, среднее значения и сумма для целочисленных чисел и для чисел с плавающей точкой. Для строк выводит размер самой большой и самой короткой строки. Выводит также всё тоже, что и -s параметр. Если будут использоваться оба параметра, и -s и -f, то выведется информация только для -f. 
Если ввести неправильно параметры программа выведет предупреждение или ошибку и сообщит о проблеме.

Про распознанение типов.
Программа распознает все форматы чисел с плавающей точкой, кроме чисел, использующих запятую в качестве разделителя. Например число "1,5" программа отнесеет к строкам, а "1.5" к числам с плавающей точкой.
Так как разделитем элементов в программе является перевод строки, то всё что не относится к вещественным или/и целым числам, будет отнесено к строкам. 
Например стока "12345 abc" - строка, а "1.25" - число с плавающей точкой. 
Если в строке встреться числа обоих типов, "floats" и "integers", то программа их также отнесет к строкам.

Данная программа была сделана в качестве тестового задания.


(EN)
This utility is designed to separate strings, integers, and floats in files. Line feed is used as a delimiter.
To run the utility, you need to use the FileContentFilter.jar file located in the out/artifacts/FileContentFilter_jar directory of this repository. The file must be downloaded and run using the command:

java -jar FileContentFilter.jar [-o path] [-p prefix] [-a] [-f|-s] file_name [file_name_1 file_name_2 ..]

where FileContentFilter.jar is the name of the utility, file_name [file_name_1 file_name_2 ..] is the name of the input file[s]. To run the program, Java 18 (62) version or higher must be installed on your computer.
The remaining parameters are auxiliary and optional.
You can run the program yourself using an IDE that supports Java version 18 (62) and higher. The input of program parameters is determined by the IDE and can be individual.
The utility always generates three output files integers.txt - for integers, floats.txt - for floating point numbers, strings.txt - for strings.

Optional Parameters

-o path - sets the path to the output files.
-p prefix - sets the prefix to the output files. For example, -p sample_, therefore the output files will be named sample_integers.txt, sample_floats.txt, sample_strings.txt.
-a - turns on the mode of appending to the file. By default, all output files are overwritten with new content.
-s - output the number of elements written to each output file.
-f - display extended statistics. Minimum, maximum, average and sum for integer and floating point numbers. For strings, displays the size of the largest and shortest string. It also outputs everything the same as the -s parameter. If both -s and -f are used, only the information for -f will be printed.
If you enter incorrect parameters, the program will display a warning or error and report a problem.

About type recognition.
The program recognizes all floating point number formats, except numbers that use a comma as a separator. For example, the program will classify the number “1.5” as strings, and “1.5” as floating point numbers.
Since the separator of elements in the program is line feed, everything that does not belong to real and/or integer numbers will be classified as strings.
For example, "12345 abc" is a string, and "1.25" is a floating point number.
If a line contains numbers of both types, "floats" and "integers", then the program will also classify them as strings.

This program was made as a test task.
