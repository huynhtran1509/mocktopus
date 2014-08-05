package com.lacronicus.mocktopus.core.mocktopus.options;

import com.lacronicus.mocktopus.core.mocktopus.Settings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;

/**
 * Created by fdoyle on 7/16/14.
 */
public interface IOptionsNode {
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions);

    public void addDefaultSettingsTo(Settings toAdd);
}
