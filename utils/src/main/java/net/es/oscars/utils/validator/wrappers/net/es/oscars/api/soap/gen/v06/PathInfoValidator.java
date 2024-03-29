package net.es.oscars.utils.validator.wrappers.net.es.oscars.api.soap.gen.v06;

import net.es.oscars.utils.validator.DataValidator;

import net.es.oscars.api.soap.gen.v06.PathInfo;

public class PathInfoValidator {
    /**
     * Validate the content of a PathInfo object. Note that object will never be null.
     * 
     * @param obj to validate
     * @throws RuntimeException
     */
    public static void validator (PathInfo obj) throws RuntimeException {

        DataValidator.validate (obj.getLayer2Info(), true);
        DataValidator.validate (obj.getLayer3Info(), true);
        DataValidator.validate (obj.getMplsInfo(), true);
        DataValidator.validate (obj.getPath(), false);
    }
}