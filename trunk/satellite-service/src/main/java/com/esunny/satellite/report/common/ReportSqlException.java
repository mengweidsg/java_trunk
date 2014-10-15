/**
 * 
 */
package com.esunny.satellite.report.common;

/**
 * @author Administrator
 * 
 */
public class ReportSqlException extends Throwable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8568343328467427597L;

    public ReportSqlException(Throwable cause)
    {
        super(cause);
    }

    public ReportSqlException(String message)
    {
        super(message);
    }

}
