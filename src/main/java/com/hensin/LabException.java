package com.hensin;

public class LabException extends Exception {

    public enum Code {
        
        DIVIDE_BY_ZERO("Деление на ноль: %s = 0"),
        NEGATIVE_SHIFT("Сдвиг не может быть отрицательным: %s"),

        ARRAY_NULL_OR_EMPTY("Массив пустой или null"),
        ARRAY_NOT_INITIALIZED("Массив не инициализирован"),
        ARRAY_SIZE_INVALID("Размер должен быть > 0, получено: %s"),
        ARRAY_SIZE_TOO_BIG("Слишком большой размер: %s"),

        TIME_OUT_OF_RANGE("time ожидалось в [0..30], получено: %s"),

        STRING_NULL("Строка %s = null"),
        SYMBOL_NULL("Символ %s = null"),
        SYMBOL_EMPTY("Символ %s не может быть пустым"),
        SYMBOL_MULTIPLE_CHARS("Символ %s должен состоять из одного символа");

        private final String template;

        Code(String template) { this.template = template; }

        public String format(Object... args) {
            return String.format(template, args);
        }
    }

    private final Code code;

    public LabException(Code code, Object... args) {
        super(code.format(args));
        this.code = code;
    }

    public Code getCode() { return code; }
}