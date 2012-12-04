package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.Min;
import play.db.ebean.Model;

@Entity
public class Task extends Model 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Required
	private String label;

	@Required
	@Min(0)
	private Integer priority;
	
	@Min(0)
	private Integer estimatedDuration;

	@ManyToOne
	private Project project;

	@Required 
	@ManyToOne
	private UserAccount user;

	private TaskStatus taskStatus = TaskStatus.NOTSTARTED;
	
	public Long getId()
	{
		return id ;
	}

	public void setId(Long _id)
	{
		id = _id ;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String _label)
	{
		label = _label ;
	}

	public Integer getPriority()
	{
		return priority ;
	}

	public void setPriority(Integer _priority)
	{
		priority = _priority ;
	}

	public Integer getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(Integer estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public Project getProject()
	{
		return project ;
	}

	public void setProject(Project _project)
	{
		project = _project ;
	}	

	public UserAccount getUser()
	{
		return user ;
	}

	public void setUser(UserAccount _user)
	{
		user = _user ;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public TaskStatus nextTaskStatus()
	{
		switch(taskStatus)
		{
			case NOTSTARTED : this.taskStatus = TaskStatus.STARTED ; break;
			case STARTED : this.taskStatus = TaskStatus.FINISHED ; break;
			case FINISHED : this.taskStatus = TaskStatus.CLOSED ; break;
			case CLOSED : this.taskStatus = TaskStatus.REOPENED ; break;
			case REOPENED : this.taskStatus = TaskStatus.FINISHED ; break;
		}
		return taskStatus ;
	}
	
	public static Finder<Long,Task> find = new Finder( Long.class, Task.class );

	public static List<Task> findAll() 
	{
		return find.all();
	}

	public static Task findById(Long id)
	{
		return find.ref(id);
	}
	
	public static void create(Task task) 
	{
		task.save();
	}

	public static void delete(Long id) 
	{
		find.ref(id).delete();
	}
}
