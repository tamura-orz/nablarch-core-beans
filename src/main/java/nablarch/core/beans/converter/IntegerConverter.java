package nablarch.core.beans.converter;

import nablarch.core.beans.ConversionException;
import nablarch.core.beans.Converter;

/**
 * {@code Integer}型への変換を行う {@link Converter} 。
 * <p/>
 * 変換元の型に応じて、以下のとおり変換を行う。
 * <p/>
 * <b>真偽値</b>：<br>
 * {@code true}であれば{@code 1}、{@code false}であれば{@code 0}を返却する。
 * <p/>
 * <b>数値型</b>：<br>
 * 変換元の数値を表す{@link Integer}オブジェクトを返却する。
 * <p/>
 * <b>文字列型</b>：<br>
 * 変換元の数値文字列を表す{@link Integer}オブジェクトを返却する。
 * 文字列が数値として不正な場合は{@link ConversionException}を送出する。
 * <p/>
 * <b>文字列型の配列</b>：<br>
 * 要素数が1であれば、その要素を{@code Integer}オブジェクトに変換して返却する。
 * 要素数が1以外であれば、{@link ConversionException}を送出する。
 * <p/>
 * <b>上記以外</b>：<br>
 * {@link ConversionException}を送出する。
 *
 * @author kawasima
 * @author tajima
 */
public class IntegerConverter implements Converter<Integer> {

    @Override
    public Integer convert(final Object value) {
        if (value instanceof Number) {
            return Number.class.cast(value).intValue();
        } else if (value instanceof String) {
            final String stringValue = String.class.cast(value);
            if (stringValue.isEmpty()) {
                return null;
            }
            try {
                return Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                throw new ConversionException(Integer.class, value);
            }
        } else if (value instanceof Boolean) {
            return Boolean.class.cast(value) ? 1 : 0;
        } else if (value instanceof String[]) {
            return SingleValueExtracter.toSingleValue((String[]) value, this, Integer.class);
        } else {
            throw new ConversionException(Integer.class, value);
        }
    }

    /**
     * {@code int}に変換するための{@link Converter}。
     * <p>
     * プリミティブへの変換を行うので、{@link IntegerConverter}で変換後の値が{@code null}の場合には、
     * {@code 0}に変換し返却する。
     */
    public static class Primitive extends IntegerConverter {

        @Override
        public Integer convert(final Object value) {
            final Integer result = super.convert(value);
            return result == null ? 0 : result;
        }
    }
}
