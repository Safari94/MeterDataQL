package com.demo.graphql.meterdataql.resources;


import com.demo.graphql.meterdataql.model.MeterData;
import com.demo.graphql.meterdataql.service.MeterDataService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.Execution;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequestMapping("/rest/data")
@RestController
public class MeterDataResource {

    @Value("classpath:meterdata.graphql")
    private Resource graphResourse;

    @Autowired
    private MeterDataService meterDataService;

    private GraphQL graphQL;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");



    @PostConstruct
    public void loadSchema() throws IOException {
        //Load schema from the file
        File schemaFile = graphResourse.getFile();
        //Parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private RuntimeWiring buildRuntimeWiring() {

        DataFetcher<List<MeterData>> allData = data ->{
            return meterDataService.findAll();
        };

        DataFetcher<List<MeterData>> byreadUnit = data ->{
            return meterDataService.findByReadUnit(data.getArgument("dRegisterRead"));
        };

        return RuntimeWiring.newRuntimeWiring()
                .type("Query",typeWiring ->
                        typeWiring
                                .dataFetcher("allData",allData)
                                .dataFetcher("byreadUnit",byreadUnit)

                ).build();
    }

    @PostMapping
    public ResponseEntity<Object> getAllMeterData(@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate, @RequestBody String query) throws ParseException {

        Date startDate1 = formatter.parse(startDate);
        Date endDate1 = formatter.parse(endDate);
        String user = "P000003";

        ArrayList<MeterData> infos = new ArrayList<>();
        if(endDate1.before(startDate1)) {

            while (endDate1.before(startDate1)){

                
                infos.add(new MeterData(user, endDate1.toString(), "A-", "0.0", "kWh"));
                infos.add(new MeterData(user, endDate1.toString(), "A+", String.valueOf(ThreadLocalRandom.current().nextDouble(4.86887121200562, 5.86887121200562)), "kWh"));
                infos.add(new MeterData(user, endDate1.toString(), "FP", String.valueOf(ThreadLocalRandom.current().nextDouble(0.191676933295843, 0.9916769332958439)), "%"));
                infos.add(new MeterData(user, endDate1.toString(), "Tensao Fase L1", String.valueOf(ThreadLocalRandom.current().nextDouble(230.964004516602, 237.964004516602)), "V"));
                infos.add(new MeterData(user, endDate1.toString(), "Tensao Fase L2", String.valueOf(ThreadLocalRandom.current().nextDouble(230.964004516602, 237.964004516602)), "V"));
                infos.add(new MeterData(user, endDate1.toString(), "Tensao Fase L3", String.valueOf(ThreadLocalRandom.current().nextDouble(230.964004516602, 237.964004516602)), "V"));
                endDate1 = DateUtils.addMinutes(endDate1, 15);
            }
        }
        else{

            while(startDate1.before(endDate1)){

               
                infos.add(new MeterData(user,startDate1.toString(), "A-", "0.0", "kWh"));
                infos.add(new MeterData(user, startDate1.toString(), "A+", String.valueOf(ThreadLocalRandom.current().nextDouble(4.86887121200562, 5.86887121200562)), "kWh"));
                infos.add(new MeterData(user, startDate1.toString(), "FP", String.valueOf(ThreadLocalRandom.current().nextDouble(0.191676933295843, 0.9916769332958439)), "%"));
                infos.add(new MeterData(user, startDate1.toString(), "Tensao Fase L1", String.valueOf(ThreadLocalRandom.current().nextDouble(230.964004516602, 237.964004516602)), "V"));
                infos.add(new MeterData(user, startDate1.toString(), "Tensao Fase L2", String.valueOf(ThreadLocalRandom.current().nextDouble(230.964004516602, 237.964004516602)), "V"));
                infos.add(new MeterData(user, startDate1.toString(), "Tensao Fase L3", String.valueOf(ThreadLocalRandom.current().nextDouble(230.964004516602, 237.964004516602)), "V"));

                startDate1= DateUtils.addMinutes(startDate1, 15);
            }
        }

        meterDataService = new MeterDataService(infos);

        ExecutionResult result=graphQL.execute(query);

        return  new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
