package com.nlp.code.python.connection;

import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.springframework.stereotype.Service;

import com.nlp.code.java.entity.ReviewEntity;

import shapeless.newtype;
/**
 * python funciton 사용
 * @author Administrator
 *
 */
@Service
public class PythonConnectionService {

	/**
	 * 자동 생성 모델 사용하기
	 * @param entity
	 * @return
	 */
	public ReviewEntity getReviewText(ReviewEntity entity) {
		
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.execfile("D:\\nlp_project\\text\\write\\write_review.py");
		
		PyFunction pyFunction = interpreter.get("sentence_generation",PyFunction.class);
	    PyObject pyobj = pyFunction.__call__(new PyString(entity.getSummary()),
	    		new PyInteger(30),new PyFloat(Double.valueOf(entity.getOverall())));
		if(pyobj != null) {
			entity.setReviewText(pyobj.toString());
		}
		return entity; 
	}
}
