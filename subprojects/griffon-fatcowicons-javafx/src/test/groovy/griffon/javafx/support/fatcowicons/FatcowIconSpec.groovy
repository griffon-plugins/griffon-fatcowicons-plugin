/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agratcow to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.javafx.support.fatcowicons

import griffon.plugins.fatcowicons.Fatcow
import javafx.embed.swing.JFXPanel
import spock.lang.Specification

/**
 * @author Andres Almiray
 */
class FatcowIconSpec extends Specification {
    static {
        new JFXPanel()
    }

    def 'Can create a FatcowIcon instance'() {
        given:
        Fatcow expected = Fatcow.ANCHOR

        expect:
        FatcowIcon icon = new FatcowIcon(expected)
        icon.fatcow == expected
        icon.size == 16
    }

    def 'Invalid FatcowIcon arguments'() {
        when:
        new FatcowIcon(arg)

        then:
        thrown(IllegalArgumentException)

        where:
        arg   | _
        null  | _
        ''    | _
        ' '   | _
        'foo' | _
    }
}
