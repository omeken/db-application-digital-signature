package ng.com.bitsystems.digitalsignature.converters;

import lombok.Synchronized;
import ng.com.bitsystems.digitalsignature.command.UploadCommand;
import ng.com.bitsystems.digitalsignature.model.Uploads;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UploadCommandToUploads implements Converter<UploadCommand, Uploads> {

    private ResultsCommandToResults resultsCommandToResults;
    private CoursesCommandToCourses coursesCommandToCourses;
    private UsersCommandToUsers usersCommandToUsers;
    private PrivateKeysCommandToPrivateKeys privateKeysCommandToPrivateKeys;
    private PublicKeysCommandToPublicKeys publicKeysCommandToPublicKeys;

    public UploadCommandToUploads(ResultsCommandToResults resultsCommandToResults, CoursesCommandToCourses coursesCommandToCourses, UsersCommandToUsers usersCommandToUsers, PrivateKeysCommandToPrivateKeys privateKeysCommandToPrivateKeys, PublicKeysCommandToPublicKeys publicKeysCommandToPublicKeys) {
        this.resultsCommandToResults = resultsCommandToResults;
        this.coursesCommandToCourses = coursesCommandToCourses;
        this.usersCommandToUsers = usersCommandToUsers;
        this.privateKeysCommandToPrivateKeys = privateKeysCommandToPrivateKeys;
        this.publicKeysCommandToPublicKeys = publicKeysCommandToPublicKeys;
    }

    @Synchronized
    @Nullable
    @Override
    public Uploads convert(UploadCommand uploadCommand) {

        if(uploadCommand == null){
            return null;
        }
        Uploads uploads = new Uploads();
        uploads.setCourse(coursesCommandToCourses.convert(uploadCommand.getCoursesCommand()));
        uploads.setOwner(usersCommandToUsers.convert(uploadCommand.getUsersCommand()));
        uploads.setPrivateKeys(privateKeysCommandToPrivateKeys.convert(uploadCommand.getPrivateKeyCommand()));
        uploads.setPublicKeys(publicKeysCommandToPublicKeys.convert(uploadCommand.getPublicKeyCommand()));

        if(uploadCommand.getResultCommand().size()>0 && uploadCommand.getCoursesCommand() != null){
            uploadCommand.getResultCommand().forEach(resultCommand -> uploads.getResults().add(resultsCommandToResults.convert(resultCommand)));
        }

        uploads.setUploadedOn(uploadCommand.getDateOfUpload());

        return uploads;
    }
}
