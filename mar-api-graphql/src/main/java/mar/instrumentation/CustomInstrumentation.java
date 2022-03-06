package mar.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomInstrumentation extends SimpleInstrumentation {

    // FIXME: handle queryies with no operation name

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        // Ignoring introspection queries, not relevant
        String operation = parameters.getOperation();
        final boolean logging = !operation.equals("IntrospectionQuery");

        long startNanos = System.nanoTime();
        var executionId = parameters.getExecutionInput().getExecutionId();

        if (logging) {
            log.info("{}, query:\n {}\n with variables: {}", executionId, parameters.getQuery(), parameters.getVariables());
        }

        return new SimpleInstrumentationContext<ExecutionResult>() {
            @Override
            public void onCompleted(ExecutionResult result, Throwable t) {
                long duration = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startNanos, TimeUnit.NANOSECONDS);

                if (!logging) return;

                if (t == null) {
                    log.info("{} completed in: {} ms", executionId, duration);
                } else {
                    log.error("{}: failed in: {} ms", executionId, duration, t);
                }
            }
        };
    }
}
