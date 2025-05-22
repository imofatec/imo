export default function buildCommentTree(comments) {
  const commentMap = new Map()

  comments.forEach((comment) => {
    commentMap.set(comment.id, { ...comment, children: [] })
  })

  const tree = []

  commentMap.forEach((comment) => {
    if (comment.parentId) {
      const parent = commentMap.get(comment.parentId)
      if (parent && parent.parentId === null) {
        parent.children.push(comment)
      }
    } else {
      tree.push(comment)
    }
  })

  return tree
}
