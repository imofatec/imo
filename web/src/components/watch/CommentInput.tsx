import { useState } from 'react'

type Props = {
  onSubmitComment?: (comment: string) => void
}

export default function CommentInput({ onSubmitComment }: Props) {
  const [commentDraft, setCommentDraft] = useState('')
  const [isCommentInputActive, setIsCommentInputActive] = useState(false)

  function handleCancelComment() {
    setCommentDraft('')
    setIsCommentInputActive(false)
  }

  function handleSubmitComment() {
    const trimmedDraft = commentDraft.trim()
    if (!trimmedDraft) return

    onSubmitComment?.(trimmedDraft)
    setCommentDraft('')
    setIsCommentInputActive(false)
  }

  return (
    <div>
      <label htmlFor="new-comment" className="text-sm font-medium text-white">
        Novo comentário
      </label>
      <input
        id="new-comment"
        type="text"
        value={commentDraft}
        onFocus={() => setIsCommentInputActive(true)}
        onChange={(event) => setCommentDraft(event.target.value)}
        className="focus:border-cyan/40 mt-3 h-11 w-full rounded-xl border border-white/10 bg-[#0C0424] px-3 text-sm text-white outline-none placeholder:text-white/35"
        placeholder="Escreva um comentario aqui..."
      />

      {isCommentInputActive && (
        <div className="mt-3 flex items-center justify-end gap-3">
          <button
            type="button"
            onClick={handleCancelComment}
            className="rounded-xl border border-white/15 bg-white/5 px-4 py-2 text-sm font-medium text-white/80 transition hover:bg-white/10 hover:text-white"
          >
            Cancelar
          </button>
          <button
            type="button"
            onClick={handleSubmitComment}
            disabled={!commentDraft.trim()}
            className="border-cyan/30 bg-cyan/10 text-cyan hover:bg-cyan/20 rounded-xl border px-4 py-2 text-sm font-medium transition disabled:cursor-not-allowed disabled:opacity-50"
          >
            Comentar
          </button>
        </div>
      )}
    </div>
  )
}
