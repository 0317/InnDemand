package demand.inn.com.inndemand.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Akash
 */
public class User implements Parcelable {


    private boolean success;
    private ResultEntity result;
    private Object error;

    protected User(Parcel in) {
        success = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultEntity getResult() {
        return result;
    }

    public Object getError() {
        return error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
    }

    public static class ResultEntity implements Parcelable {
        private int code;
        private String message;
        /**
         * name : Yo Yo VJ Singh
         * sex : false
         * profilePhotos : [""]
         * age : 36
         * trustScore : {"facbook":false,"phoneNo":false,"photoId":false,"totalTrustScore":0}
         * learn : [{"skillName":"acting","_id":"56c6b0bf94cd652707d2f567","avgRating":0,"connectedList":[]},{"skillName":"archery","_id":"56c6b0bf94cd652707d2f566","avgRating":0,"connectedList":[]},{"skillName":"b-boying","_id":"56c6b0bf94cd652707d2f565","avgRating":0,"connectedList":[]},{"skillName":"aerobics","_id":"56c6b0bf94cd652707d2f564","avgRating":0,"connectedList":[]},{"skillName":"baking","_id":"56c6b0bf94cd652707d2f563","avgRating":0,"connectedList":[]},{"skillName":"ballroom dance","_id":"56c6b0bf94cd652707d2f562","avgRating":0,"connectedList":[]},{"skillName":"basketball","_id":"56c6b0bf94cd652707d2f561","avgRating":0,"connectedList":[]}]
         * teach : [{"skillName":"acting","_id":"56c6b0bf94cd652707d2f56e","connectedList":[],"photoGallery":[],"avgRating":0},{"skillName":"archery","_id":"56c6b0bf94cd652707d2f56d","connectedList":[],"photoGallery":[],"avgRating":0},{"skillName":"badminton","_id":"56c6b0bf94cd652707d2f56c","connectedList":[],"photoGallery":[],"avgRating":0},{"skillName":"ballet","_id":"56c6b0bf94cd652707d2f56b","connectedList":[],"photoGallery":[],"avgRating":0},{"skillName":"baking","_id":"56c6b0bf94cd652707d2f56a","connectedList":[],"photoGallery":[],"avgRating":0},{"skillName":"b-boying","_id":"56c6b0bf94cd652707d2f569","connectedList":[],"photoGallery":[],"avgRating":0},{"skillName":"basketball","_id":"56c6b0bf94cd652707d2f568","connectedList":[],"photoGallery":[],"avgRating":0}]
         * partner : [{"skillName":"aerobics","_id":"56c6b0bf94cd652707d2f560","connectedList":[],"avgRating":0},{"skillName":"b-boying","_id":"56c6b0bf94cd652707d2f55f","connectedList":[],"avgRating":0},{"skillName":"baking","_id":"56c6b0bf94cd652707d2f55e","connectedList":[],"avgRating":0},{"skillName":"archery","_id":"56c6b0bf94cd652707d2f55d","connectedList":[],"avgRating":0},{"skillName":"ballroom dance","_id":"56c6b0bf94cd652707d2f55c","connectedList":[],"avgRating":0},{"skillName":"ballet","_id":"56c6b0bf94cd652707d2f55b","connectedList":[],"avgRating":0},{"skillName":"calligraphy","_id":"56c6b0bf94cd652707d2f55a","connectedList":[],"avgRating":0},{"skillName":"candle decoration","_id":"56c6b0bf94cd652707d2f559","connectedList":[],"avgRating":0},{"skillName":"bollywood-dance","_id":"56c6b0bf94cd652707d2f558","connectedList":[],"avgRating":0}]
         */

        private List<ResultsEntity> results;

        protected ResultEntity(Parcel in) {
            code = in.readInt();
            message = in.readString();
        }

        public static final Creator<ResultEntity> CREATOR = new Creator<ResultEntity>() {
            @Override
            public ResultEntity createFromParcel(Parcel in) {
                return new ResultEntity(in);
            }

            @Override
            public ResultEntity[] newArray(int size) {
                return new ResultEntity[size];
            }
        };

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setResults(List<ResultsEntity> results) {
            this.results = results;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public List<ResultsEntity> getResults() {
            return results;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(code);
            dest.writeString(message);
        }

        public static class ResultsEntity implements Parcelable {
            private String name;
            private boolean sex;
            private String age;
            /**
             * facbook : false
             * phoneNo : false
             * photoId : false
             * totalTrustScore : 0
             */

            private TrustScoreEntity trustScore;
            private List<String> profilePhotos;
            /**
             * skillName : acting
             * _id : 56c6b0bf94cd652707d2f567
             * avgRating : 0
             * connectedList : []
             */

            private List<LearnEntity> learn;
            /**
             * skillName : acting
             * _id : 56c6b0bf94cd652707d2f56e
             * connectedList : []
             * photoGallery : []
             * avgRating : 0
             */

            private List<TeachEntity> teach;
            /**
             * skillName : aerobics
             * _id : 56c6b0bf94cd652707d2f560
             * connectedList : []
             * avgRating : 0
             */

            private List<PartnerEntity> partner;

            protected ResultsEntity(Parcel in) {
                name = in.readString();
                sex = in.readByte() != 0;
                age = in.readString();
                profilePhotos = in.createStringArrayList();
            }

            public static final Creator<ResultsEntity> CREATOR = new Creator<ResultsEntity>() {
                @Override
                public ResultsEntity createFromParcel(Parcel in) {
                    return new ResultsEntity(in);
                }

                @Override
                public ResultsEntity[] newArray(int size) {
                    return new ResultsEntity[size];
                }
            };

            public void setName(String name) {
                this.name = name;
            }

            public void setSex(boolean sex) {
                this.sex = sex;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public void setTrustScore(TrustScoreEntity trustScore) {
                this.trustScore = trustScore;
            }

            public void setProfilePhotos(List<String> profilePhotos) {
                this.profilePhotos = profilePhotos;
            }

            public void setLearn(List<LearnEntity> learn) {
                this.learn = learn;
            }

            public void setTeach(List<TeachEntity> teach) {
                this.teach = teach;
            }

            public void setPartner(List<PartnerEntity> partner) {
                this.partner = partner;
            }

            public String getName() {
                return name;
            }

            public boolean isSex() {
                return sex;
            }

            public String getAge() {
                return age;
            }

            public TrustScoreEntity getTrustScore() {
                return trustScore;
            }

            public List<String> getProfilePhotos() {
                return profilePhotos;
            }

            public List<LearnEntity> getLearn() {
                return learn;
            }

            public List<TeachEntity> getTeach() {
                return teach;
            }

            public List<PartnerEntity> getPartner() {
                return partner;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
                dest.writeByte((byte) (sex ? 1 : 0));
                dest.writeString(age);
                dest.writeStringList(profilePhotos);
            }

            public static class TrustScoreEntity {
                private boolean facbook;
                private boolean phoneNo;
                private boolean photoId;
                private int totalTrustScore;

                public void setFacbook(boolean facbook) {
                    this.facbook = facbook;
                }

                public void setPhoneNo(boolean phoneNo) {
                    this.phoneNo = phoneNo;
                }

                public void setPhotoId(boolean photoId) {
                    this.photoId = photoId;
                }

                public void setTotalTrustScore(int totalTrustScore) {
                    this.totalTrustScore = totalTrustScore;
                }

                public boolean isFacbook() {
                    return facbook;
                }

                public boolean isPhoneNo() {
                    return phoneNo;
                }

                public boolean isPhotoId() {
                    return photoId;
                }

                public int getTotalTrustScore() {
                    return totalTrustScore;
                }
            }

            public static class LearnEntity {
                private String skillName;
                private String _id;
                private int avgRating;
                private List<?> connectedList;

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public void setAvgRating(int avgRating) {
                    this.avgRating = avgRating;
                }

                public void setConnectedList(List<?> connectedList) {
                    this.connectedList = connectedList;
                }

                public String getSkillName() {
                    return skillName;
                }

                public String get_id() {
                    return _id;
                }

                public int getAvgRating() {
                    return avgRating;
                }

                public List<?> getConnectedList() {
                    return connectedList;
                }
            }

            public static class TeachEntity {
                private String skillName;
                private String _id;
                private int avgRating;
                private List<?> connectedList;
                private List<?> photoGallery;

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public void setAvgRating(int avgRating) {
                    this.avgRating = avgRating;
                }

                public void setConnectedList(List<?> connectedList) {
                    this.connectedList = connectedList;
                }

                public void setPhotoGallery(List<?> photoGallery) {
                    this.photoGallery = photoGallery;
                }

                public String getSkillName() {
                    return skillName;
                }

                public String get_id() {
                    return _id;
                }

                public int getAvgRating() {
                    return avgRating;
                }

                public List<?> getConnectedList() {
                    return connectedList;
                }

                public List<?> getPhotoGallery() {
                    return photoGallery;
                }
            }

            public static class PartnerEntity {
                private String skillName;
                private String _id;
                private int avgRating;
                private List<?> connectedList;

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public void setAvgRating(int avgRating) {
                    this.avgRating = avgRating;
                }

                public void setConnectedList(List<?> connectedList) {
                    this.connectedList = connectedList;
                }

                public String getSkillName() {
                    return skillName;
                }

                public String get_id() {
                    return _id;
                }

                public int getAvgRating() {
                    return avgRating;
                }

                public List<?> getConnectedList() {
                    return connectedList;
                }
            }
        }
    }
}
