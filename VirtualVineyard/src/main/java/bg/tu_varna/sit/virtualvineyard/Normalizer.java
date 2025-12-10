package bg.tu_varna.sit.virtualvineyard;

public interface Normalizer {
    default String normalize(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString().trim();
    }

    default boolean isEnglishAndNumbersOnly(String text) {
        if (text == null) return false;

        //a-z, A-Z, 0-9, and -
        return text.matches("^[a-zA-Z0-9-]+$");
    }
}
