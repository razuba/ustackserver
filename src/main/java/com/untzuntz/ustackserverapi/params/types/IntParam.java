package com.untzuntz.ustackserverapi.params.types;

import com.untzuntz.ustackserverapi.APIException;
import com.untzuntz.ustackserverapi.params.exceptions.ParamValueException;

public class IntParam extends BaseParam implements ParameterDefinitionInt<Integer>
{
	private Integer minVal;
	private Integer maxVal;

	/**
	 * Setup core Integer parameter
	 * 
	 * @param n
	 * @param d
	 */
	public IntParam(String n, String d) {
		super(n, d);
	}
	
	/**
	 * Setup an integer parameter with a min and max value
	 * @param minVal
	 * @param maxVal
	 */
	public IntParam(String n, String d, Integer minVal, Integer maxVal)
	{
		super(n, d);
		this.minVal = minVal;
		this.maxVal = maxVal;
	}

	/**
	 * Setup a integer parameter with just a maximum value
	 * @param minLen
	 */
	public IntParam(String n, String d, Integer maxVal)
	{
		super(n, d);
		this.maxVal = maxVal;
	}

	public String getTypeDescription() {
		
		String mv = "" + minVal;
		if (minVal == null)
			mv = "-2^31";
		String mxv = "" + maxVal;
		if (maxVal == null)
			mxv = "2^31";
		
		return "A number between " + mv + " and " + mxv;
	}
	
	public Integer getMinVal() {
		return minVal;
	}

	public void setMinVal(Integer minVal) {
		this.minVal = minVal;
	}

	public Integer getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(Integer maxVal) {
		this.maxVal = maxVal;
	}

	@Override
	public void validate(String data) throws APIException {
		
		if (data == null && minVal != null)
			throw new ParamValueException(this, "Integer must be greater than " + minVal);

		int intVal = -1;
		try {
			intVal = Integer.valueOf(data);
		} catch (NumberFormatException nfe) {
			throw new ParamValueException(this, "Field must have a valid value");
		}
		
		if (minVal != null && intVal < minVal)
			throw new ParamValueException(this, "Integer must be greater than " + minVal);
	
		if (maxVal != null && intVal > maxVal)
			throw new ParamValueException(this, "Integer must be less than " + maxVal);
		
	}

	@Override
	public Integer getValue(String data) {
		try {
			
			int ret = Integer.valueOf(data);
			if (minVal != null && ret < minVal)
				ret = minVal;
			if (maxVal != null && ret > maxVal)
				ret = maxVal;
			
			return ret; 
			
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

}
