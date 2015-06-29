import com.fangger.dao.TestDAO;
import com.fangger.model.XiaoQu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by p0po on 15-6-14.
 */
@Service
public class TestService {
    @Autowired
    TestDAO testDAO;

    public List<XiaoQu> testSelect(){
        return testDAO.getAll();
    }
    


}
