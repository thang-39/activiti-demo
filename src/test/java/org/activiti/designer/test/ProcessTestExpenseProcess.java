package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestExpenseProcess {

	private String filename = "C:\\Users\\thanglaptop\\eclipse-workspace\\demo\\src\\main\\resources\\diagrams\\expense_process.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("expenseProcess.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Kermit");
		variableMap.put("amount", 123);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("expenseProcess", variableMap);
		assertNotNull(processInstance.getId());
		
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("Request refund",task.getName());
		
	}
}