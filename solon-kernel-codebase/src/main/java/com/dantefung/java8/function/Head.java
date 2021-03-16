package com.dantefung.java8.function;

import lombok.*;

/**
 * @author Javacfox
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Head {
    private String actionType;
    private String currentTime;
}