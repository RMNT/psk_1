package rest.contracts;

import entities.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class EventDto {

    private String Title;

    private String ClientTitle;

    private List<ModeratorDto> moderators;
}
