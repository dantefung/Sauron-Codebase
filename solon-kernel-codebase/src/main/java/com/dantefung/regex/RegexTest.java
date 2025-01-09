package com.dantefung.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fenghaolin
 * @date 2025/01/09 14/29
 * @since JDK1.8
 */
public class RegexTest {

    public static void main(String[] args) {
        // 假设这是你的输入字符串
        String input = "<![CDATA[test]]>";

        // 定义一个正则表达式模式来匹配 CDATA 中的内容
        // (?<=...) 是肯定先行断言，表示前面必须有指定的字符但不包括在匹配结果中
        // (?=...) 是肯定后行断言，表示后面必须有指定的字符但不包括在匹配结果中
        // .+ 匹配任意字符（除换行符外），至少一次
        String patternString = "(?<=<!\\[CDATA\\[).+(?=\\]\\]>)";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        // 查找匹配项
        if (matcher.find()) {
            // 提取匹配的内容
            String cdataContent = matcher.group();
            System.out.println("Extracted CDATA content: " + cdataContent);
        } else {
            System.out.println("No match found.");
        }

        test2(input, pattern);
    }

    /**
     * `Pattern.compile("[&<>]")` 是 Java 中用于编译正则表达式的调用，它创建了一个 `Pattern` 对象，这个对象包含了你指定的正则表达式模式。在这个例子中，正则表达式模式是 `[&<>]`。
     *
     * 让我们分解一下这个正则表达式：
     *
     * - `[...]`：定义一个字符类（character class）。这意味着匹配方括号内的任何一个字符。
     * - `&`：这是方括号内的一个字符，表示要匹配实际的 '&' 字符。
     * - `<`：这是方括号内的另一个字符，表示要匹配实际的 '<' 字符。
     * - `>`：这是方括号内的最后一个字符，表示要匹配实际的 '>' 字符。
     *
     * 因此，整个正则表达式 `[&<>]` 的意思是匹配任何单独出现的 '&'、'<' 或者 '>' 字符。每当输入字符串中有这些字符中的任何一个时，使用这个模式的匹配器将会找到它们。
     *
     * 例如，如果你有一个字符串 `"Hello & World <XML> Example"` 并使用上述模式去查找，那么匹配器将会找到三个匹配项：`&`、`<` 和 `>`。
     *
     * 在实践中，这样的模式常用于对HTML或XML等标记语言进行简单的文本处理，比如清理文本中的特殊字符或者为这些字符做实体转义。不过需要注意的是，对于完整的HTML或XML解析和处理，应该使用专门的解析库，而不是仅仅依赖于正则表达式，因为标记语言的结构可能非常复杂，而正则表达式并不总是适合处理这种复杂性。
     * @param input
     * @param pattern
     */
    private static void test2(String input, Pattern pattern) {
        Pattern pattern2 = Pattern.compile("[&<>]");
        Matcher matcher2 = pattern.matcher(input);
        System.out.println(matcher2.find());
    }


}
