function TreeNode(id , value) {
	this.level = 0;
	this.id = id;
    this.value = value;
    this.size = 0;
    this.children = [];
    this.parent = null;
};

TreeNode.prototype.addChild = function(child) {
    this.children[this.size] = child;
    this.size++;
    child.parent = this;
    child.level = this.level + 1;
}

TreeNode.prototype.hasChildren = function() {
    return size == 0;
}