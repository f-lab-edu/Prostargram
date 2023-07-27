package flab.project.data.dto.domain;

public class Option {

    protected final long optionId;

    protected final String optionContent;

    public Option(long optionId, String optionContent) {
        this.optionId = optionId;
        this.optionContent = optionContent;
    }
}
