package coz.weavon.core.project.domain.model;

import java.util.List;

public record Projects(List<Project> value) {
    public static Projects of(List<Project> projects) {
        return new Projects(projects);
    }
}
