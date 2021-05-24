package rest.contracts;

import entities.Event;
import entities.Moderator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class EventDto {

    private String Title;

    private String ClientTitle;

    private Integer contractNumber;

    //private String moderators;
}