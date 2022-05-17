package view;

import java.sql.CallableStatement;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.ValueChangeEvent;

import model.service.AppModuleImpl;

import model.view.HolidayCalendarSearchVOImpl;
import model.view.HolidayCalendarSearchVORowImpl;

import model.view.SpCalendarHVORowImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

public class ManagedBean {
    private RichColumn hcDay;
    private RichColumn hcDate;
    private RichColumn hcWorkingStatus;
    private RichColumn hcLastUpdatedBy;
    private RichColumn hcLastUpdateDate;
    private RichColumn hcHolidayDescription;
    public ManagedBean() {
     
     // System.out.println("========================== maanaged bean initialized  ====================== ");
    }
    
    AppModuleImpl appM = getAppModuleImpl();

    public AppModuleImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("AppModuleDataControl"); // Name of application module in datacontrolBinding.cpx
        AppModuleImpl appM = (AppModuleImpl)dc.getDataProvider();
        return appM;
    }
    
    public void onPageLoad(PhaseEvent phaseEvent) {
        // Add event code here...

        if (!AdfFacesContext.getCurrentInstance().isPostback()) {

           //  System.out.println("========================== in on page load mmm  ====================== ");
         
             setCurrentMonthFromList();   
             
             updateHolidayOfHolidayCalendar();
             
        
        }

    }


 private void setCurrentMonthFromList() {    
        
   //   System.out.println("  ========================== in setCurrentMonth()     ==========================" );

        HolidayCalendarSearchVOImpl searchVo = appM.getHolidayCalendarSearchVO1(); 
        HolidayCalendarSearchVORowImpl currentRow ,firstRow ;
      
        firstRow = (HolidayCalendarSearchVORowImpl)searchVo.first();  // only one row
        searchVo.setCurrentRow(firstRow);
        currentRow = (HolidayCalendarSearchVORowImpl)searchVo.getCurrentRow();
        currentRow.setCurrentMonthFromMonthList(); 
        
        
    }
    
    private void executeOperation(String method) {
        BindingContainer bindings = getBindings();
               OperationBinding ob = bindings.getOperationBinding(method);
        Object result = ob.execute();
        
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    private void updateHolidayOfHolidayCalendar() {

        HolidayCalendarSearchVOImpl searchVo = appM.getHolidayCalendarSearchVO1(); 
        HolidayCalendarSearchVORowImpl currentRow ,firstRow ;
        
        firstRow = (HolidayCalendarSearchVORowImpl)searchVo.first();  // only one row
        searchVo.setCurrentRow(firstRow);
        currentRow = (HolidayCalendarSearchVORowImpl)searchVo.getCurrentRow();
        
        currentRow.updateHolidayForHolidayCalendar();
        
        
    }

    public void WorkingStatusValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcDay);  
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcWorkingStatus);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcHolidayDescription);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcLastUpdateDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcLastUpdatedBy);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.hcLastUpdatedBy);
    }

    public void setHcDay(RichColumn hcDay) {
        this.hcDay = hcDay;
    }

    public RichColumn getHcDay() {
        return hcDay;
    }

    public void setHcDate(RichColumn hcDate) {
        this.hcDate = hcDate;
    }

    public RichColumn getHcDate() {
        return hcDate;
    }

    public void setHcWorkingStatus(RichColumn hcWorkingStatus) {
        this.hcWorkingStatus = hcWorkingStatus;
    }

    public RichColumn getHcWorkingStatus() {
        return hcWorkingStatus;
    }

    public void setHcLastUpdatedBy(RichColumn hcLastUpdatedBy) {
        this.hcLastUpdatedBy = hcLastUpdatedBy;
    }

    public RichColumn getHcLastUpdatedBy() {
        return hcLastUpdatedBy;
    }

    public void setHcLastUpdateDate(RichColumn hcLastUpdateDate) {
        this.hcLastUpdateDate = hcLastUpdateDate;
    }

    public RichColumn getHcLastUpdateDate() {
        return hcLastUpdateDate;
    }

    public void setHcHolidayDescription(RichColumn hcHolidayDescription) {
        this.hcHolidayDescription = hcHolidayDescription;
    }

    public RichColumn getHcHolidayDescription() {
        return hcHolidayDescription;
    }

    public void save(ActionEvent actionEvent) {
        // Add event code here...
        this.executeOperation("Commit");
        
        updateHolidayOfHolidayCalendar();
        
    }
}
