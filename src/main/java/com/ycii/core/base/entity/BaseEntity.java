package com.ycii.core.base.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
    @Column(name = "create_time", nullable = true)
	private Date createTime;
    @Column(name = "create_user", length = 32, nullable = true)
    private String createUser;
    @Column(name = "update_time", nullable = true)
	private Date updateTime;
    @Column(name = "update_user", length = 32, nullable = true)
	private String updateUser;
    public Date getCreateTime()
    {
        return createTime;
    }
    public String getCreateUser()
    {
        return createUser;
    }
    public String getId()
    {
        return id;
    }
    public Date getUpdateTime()
    {
        return updateTime;
    }
    public String getUpdateUser()
    {
        return updateUser;
    }
    public void setCreateTime( Date createTime )
    {
        this.createTime = createTime;
    }
    public void setCreateUser( String createUser )
    {
        this.createUser = createUser;
    }
    public void setId( String id )
    {
        this.id = id;
    }
    public void setUpdateTime( Date updateTime )
    {
        this.updateTime = updateTime;
    }
    public void setUpdateUser( String updateUser )
    {
        this.updateUser = updateUser;
    }

}