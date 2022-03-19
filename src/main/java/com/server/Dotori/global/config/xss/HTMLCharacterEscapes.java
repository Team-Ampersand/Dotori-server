package com.server.Dotori.global.config.xss;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HTMLCharacterEscapes extends CharacterEscapes {

    private final int[] asciiEscapes;

    private final CharSequenceTranslator translator;

    public HTMLCharacterEscapes() {
        Map<CharSequence, CharSequence> customMap = new HashMap<CharSequence, CharSequence>();
        customMap.put("<", "&#60;");
        customMap.put(">", "&#62;");
        customMap.put("$", "&#36;");
        customMap.put(";", "&#59;");
        customMap.put("`", "&#96;");
        Map<CharSequence, CharSequence> CUSTOM_ESCAPE = Collections.unmodifiableMap(customMap);

        // 1. XSS 방지 처리할 특수 문자 지정
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['$'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[';'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['`'] = CharacterEscapes.ESCAPE_CUSTOM;

        // 2. XSS 방지 처리 특수 문자 인코딩 값 지정
        translator = new AggregateTranslator(
                new LookupTranslator(EntityArrays.BASIC_ESCAPE),  // <, >, &, " 는 여기에 포함됨
                new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
                new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE),
                new LookupTranslator(CUSTOM_ESCAPE)
        );
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        return new SerializedString(translator.translate(Character.toString((char) ch)));
    }
}
