require 'java'

include_class 'spring.core.MessageProvider'

class JRubyMessageProvider

    @@message = "Hello world!! provided by JRuby"

    def setMessage(message)
        @@message = message
    end

    def getMessage
        @@message
    end
end

JRubyMessageProvider.new
