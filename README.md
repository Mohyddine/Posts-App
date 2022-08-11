Posts-App
First page: Activity that holds an array of posts retrieved from a dummy web-service you will be given.
Second page: Activity that holds details of this post.
A floating action button in the first page that opens bottom sheet fragment to create a post once, when you use the create post api you should load the object returned from the response manually, don't call the getPost api again since it wont return the newly created post.
Edit your post api you can only edit the posts retrieved by the getPost api if you create a new post you cannot edit it. Delete api when called will return an empty array, you need to delete the post from the list manually after calling the api.
