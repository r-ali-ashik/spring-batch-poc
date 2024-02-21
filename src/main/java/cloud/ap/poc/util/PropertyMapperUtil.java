package cloud.ap.poc.util;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.InvocationTargetException;

public class PropertyMapperUtil {
    private static ModelMapper modelMapper;

    private static ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setAmbiguityIgnored(true)
                    .setSkipNullEnabled(true);
        }
        return modelMapper;
    }

    public static <S, D> D map(S source, Class<D> destinationType) {
        return getModelMapper().map(source, destinationType);
    }

    public static void nullAwareMap(Object source, Object destination) throws IllegalAccessException, InvocationTargetException {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if (value != null) {
                    super.copyProperty(dest, name, value);
                }
            }
        }.copyProperties(destination, source);
    }
}