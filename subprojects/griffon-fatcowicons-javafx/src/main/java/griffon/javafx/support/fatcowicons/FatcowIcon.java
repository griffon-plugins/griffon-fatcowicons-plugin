/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.javafx.support.fatcowicons;

import griffon.plugins.fatcowicons.Fatcow;
import javafx.scene.image.Image;

import javax.annotation.Nonnull;
import java.net.URL;

import static griffon.plugins.fatcowicons.Fatcow.invalidDescription;
import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public class FatcowIcon extends Image {
    private static final String ERROR_FATCOW_NULL = "Argument 'fatcow' must not be null.";
    private Fatcow fatcow;
    private int size;

    public FatcowIcon(@Nonnull Fatcow fatcow) {
        this(fatcow, 16);
    }

    public FatcowIcon(@Nonnull Fatcow fatcow, int size) {
        super(toURL(fatcow, size), true);
        this.fatcow = requireNonNull(fatcow, ERROR_FATCOW_NULL);
        this.size = size;
    }

    public FatcowIcon(@Nonnull String description) {
        super(toURL(description));
        this.fatcow = Fatcow.findByDescription(description);

        String[] parts = description.split(":");
        if (parts.length == 2) {
            try {
                this.size = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw invalidDescription(description, e);
            }
        } else if (size == 0) {
            size = 16;
        }
    }

    @Nonnull
    private static String toURL(@Nonnull Fatcow fatcow, int size) {
        requireNonNull(fatcow, ERROR_FATCOW_NULL);
        String resource = fatcow.asResource(size);
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalArgumentException("Icon " + fatcow + ":" + size + " does not exist");
        }
        return url.toExternalForm();
    }

    @Nonnull
    private static String toURL(@Nonnull String description) {
        requireNonBlank(description, "Argument 'description' must not be blank");
        String resource = Fatcow.asResource(description);
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalArgumentException("Icon " + description + " does not exist");
        }
        return url.toExternalForm();
    }

    @Nonnull
    public Fatcow getFatcow() {
        return fatcow;
    }

    public int getSize() {
        return size;
    }
}