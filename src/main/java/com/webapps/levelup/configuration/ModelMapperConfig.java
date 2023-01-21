package com.webapps.levelup.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    /**
     * Create Single DTO model.
     *
     * @param entity   T
     * @param outClass Class
     * @param <D>      Object
     * @param <T>      Object
     * @return Object
     */
    public <D, T> D map(final T entity, Class<D> outClass) {
        return this.getModelMapper().map(entity, outClass);
    }

    /**
     * Create List DTO model.
     *
     * @param entityList Collection
     * @param outClass   Class
     * @param <D>        Object
     * @param <T>        Object
     * @return List
     */
    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass) {
        return entityList.stream().map(entity -> map(entity, outClass)).collect(Collectors.toList());
    }

    /**
     * Add property map.
     *
     * @param am  PropertyMap S D
     * @param <S> S
     * @param <D> D
     */
    public <S, D> void addMap(PropertyMap<S, D> am) {
        this.getModelMapper().addMappings(am);
    }

    /**
     * Set strategy.
     * MatchingStrategies.LOOSE
     * MatchingStrategies.STANDARD
     * MatchingStrategies.STRICT
     *
     * @param strategy MatchingStrategy
     */
    public void setStrategy(MatchingStrategy strategy) {
        this.getModelMapper().getConfiguration().setMatchingStrategy(strategy);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
