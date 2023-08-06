package flab.project.config;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    // [ 변수 ]
    // 자식 노드 맵
    private Map<Character, TrieNode> childNodes = new HashMap<>();
    // 마지막 글자인지 여부
    private boolean isLastChar;

    // [ GETTER / SETTER 메서드 ]
    // 자식 노드 맵 Getter
    Map<Character, TrieNode> getChildNodes() {
        return this.childNodes;
    }
    // 마지막 글자인지 여부 Getter
    boolean isLastChar() {
        return this.isLastChar;
    }
    // 마지막 글자인지 여부 Setter
    void setIsLastChar(boolean isLastChar) {
        this.isLastChar = isLastChar;
    }
}
