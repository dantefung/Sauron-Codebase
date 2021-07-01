package com.dantefung.junit;

import com.dantefung.junit.beans.Person;
import com.dantefung.junit.beans.Types;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

/**
 * @Description: 将聚合参数的ArgumentsAccessor实例转成bean
 */
public class PersonAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {

        Person person = new Person();
        person.setFirstName(arguments.getString(0));
        person.setLastName(arguments.getString(1));
        person.setType(arguments.get(2, Types.class));

        return person;
    }
}