import java.io._
import java.util
import java.util.StringTokenizer

object RopeProblem {

  class Rope(val s: String) {
    root = create(s)
    var root: Vertex = null

    def process(i: Int, j: Int, k: Int) {
      val leftMid = split(root, i)
      val midRight = split(leftMid.right, j - i + 2)
      val mid = midRight.left
      root = merge(leftMid.left, midRight.right)
      val leftRight = split(root, k + 1)
      root = merge(leftRight.left, mid)
      root = merge(root, leftRight.right)
    }

    def result: String = {
      val buf = new StringBuilder
      print(root, buf, new util.Stack[Vertex])
      buf.toString
    }

    override def toString: String = {
      val stringBuilder = new StringBuilder
      print(root, stringBuilder, new util.Stack[Vertex]).toString
    }
  }

  @throws[IOException]
  def main(args: Array[String]) {
    run()
  }

  @throws[IOException]
  def run() {
    val in = new RopeProblemJ#FastScanner
    val out = new PrintWriter(System.out)
    val rope = new RopeProblemJ.Rope(in.next)
    var q = in.nextInt
    while (q > 0) {
      {
        val i = in.nextInt
        val j = in.nextInt
        val k = in.nextInt
        rope.process(i + 1, j + 1, k)
      }
      {
        q -= 1; q + 1
      }
    }
    out.println(rope.result)
    out.close()
  }

  def print(n: Vertex, buf: StringBuilder, stack: util.Stack[Vertex]): StringBuilder = {
    var node = n
    while (node != null) {
      stack.push(node)
      node = node.left
    }
    // traverse the tree
    while (stack.size > 0) {
      // visit the top node
      node = stack.pop
      buf.append(node.value)
      if (node.right != null) {
        node = node.right
        // the next node to be visited is the leftmost
        while (node != null) {
          stack.push(node)
          node = node.left
        }
      }
    }
    buf
  }

  def create(string: String): Vertex = {
    var root: Vertex = null
    var prev: Vertex = null
    val le = string.length
    var i = 0
    while (i < le) {
      {
        val v = new Vertex(le - i, string.charAt(i), null, null, prev)
        if (prev == null) root = v
        else prev.right = v
        prev = v
      }
      {
        i += 1; i - 1
      }
    }
    root
  }

  // Vertex of a splay tree
  class Vertex(// Sum of all the keys in the subtree - remember to update
               // it after each operation that changes the tree.
               var sum: Int,
               var value: Char,
               var left: Vertex,
               var right: Vertex, var parent: Vertex) {

    override def toString: String = String.valueOf(value) + ":" + print(this, new StringBuilder, new util.Stack[Vertex])
  }

  def update(v: Vertex) {
    if (v == null) return
    v.sum = 1 + (if (v.left != null) v.left.sum
    else 0) + (if (v.right != null) v.right.sum
    else 0)
    if (v.left != null) v.left.parent = v
    if (v.right != null) v.right.parent = v
  }

  def smallRotation(v: Vertex) {
    val parent = v.parent
    if (parent == null) return
    val grandparent = v.parent.parent
    if (parent.left eq v) {
      val m = v.right
      v.right = parent
      parent.left = m
    }
    else {
      val m = v.left
      v.left = parent
      parent.right = m
    }
    update(parent)
    update(v)
    v.parent = grandparent
    if (grandparent != null) if (grandparent.left eq parent) grandparent.left = v
    else grandparent.right = v
  }

  def bigRotation(v: Vertex) {
    if ((v.parent.left eq v) && (v.parent.parent.left eq v.parent)) {
      // Zig-zig
      smallRotation(v.parent)
      smallRotation(v)
    }
    else if ((v.parent.right eq v) && (v.parent.parent.right eq v.parent)) {
      // Zig-zig
      smallRotation(v.parent)
      smallRotation(v)
    }
    else {
      // Zig-zag
      smallRotation(v)
      smallRotation(v)
    }
  }

  // Makes splay of the given vertex and returns the new root.
  def splay(v: Vertex): Vertex = {
    if (v == null) return null
    var shouldLoop = true
    while (v.parent != null && shouldLoop) {
      if (v.parent.parent == null) {
        smallRotation(v)
        shouldLoop = false
      }
      if (shouldLoop) bigRotation(v)
    }
    v
  }

  class VertexPair() {
    var left: Vertex = _
    var right: Vertex = _

    def this(left: Vertex, right: Vertex) {
      this()
      this.left = left
      this.right = right
    }

    override def toString: String = "{" + left + "} + {" + right + "}"
  }

  // Searches for the given key in the tree with the given root
  // and calls splay for the deepest visited node after that.
  // Returns pair of the result and the new root.
  // If found, result is a pointer to the node with the given key.
  // Otherwise, result is a pointer to the node with the smallest
  // bigger key (next value in the order).
  // If the key is bigger than all keys in the tree,
  // then result is null.
  def find(r: Vertex, t: Int): VertexPair = {
    if (r.sum < t) return null
    var target = t
    var root = r
    var v = root
    var last = v
    var found: Vertex = null
    var shouldLoop = true
    while (v != null && shouldLoop) {
      last = v
      var leftSum = 0
      if (v.left != null) leftSum = v.left.sum
      if (target == leftSum + 1) {
        found = v
        shouldLoop = false
      }
      else if (target > leftSum) {
        v = v.right
        target -= leftSum + 1
      }
      else v = v.left
    }
    root = splay(last)
    new VertexPair(found, root)
  }

  def split(root: Vertex, key: Int): VertexPair = {
    var newRoot = root
    val result = new VertexPair()
    val findAndRoot = find(newRoot, key)
    newRoot = findAndRoot.right
    result.right = findAndRoot.left
    if (result.right == null) {
      result.left = newRoot
      return result
    }
    result.right = splay(result.right)
    result.left = result.right.left
    result.right.left = null
    if (result.left != null) result.left.parent = null
    update(result.left)
    update(result.right)
    result
  }

  def merge(left: Vertex, right: Vertex): Vertex = {
    if (left == null) return right
    if (right == null) return left
    var newRight = right
    while (newRight.left != null) newRight = newRight.left
    newRight = splay(newRight)
    newRight.left = left
    update(newRight)
    newRight
  }

  class FastScanner(val stream: InputStream) {

    var br: BufferedReader = _
    var st: StringTokenizer = _

    try
      br = new BufferedReader(new InputStreamReader(stream))

    catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }

    def next: String = {
      while (st == null || !st.hasMoreTokens)
        try
          st = new StringTokenizer(br.readLine)

        catch {
          case e: IOException => {
            e.printStackTrace()
          }
        }
      st.nextToken
    }

    def nextInt: Int = next.toInt
    def nextLong: Long = next.toLong
  }
}