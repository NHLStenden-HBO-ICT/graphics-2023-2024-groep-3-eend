package proeend.misc;

    public enum ObjectPaths {
        DUCK("project_eend/Models/Rubber_Duck_obj.obj"),
        ICOSPHERE("project_eend/Models/icotest.obj"),
        LEGO("project_eend/Models/lego.obj"),
        UVSPHERE("project_eend/Models/uvSphere.obj");

        private final String path;

        ObjectPaths(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
}
