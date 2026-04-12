import { zodResolver } from '@hookform/resolvers/zod'
import { LoaderCircle } from 'lucide-react'
import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { showRequestErrorToast } from '@/lib/requestToast'
import type { CommentData } from '@/schemas/comments/commentSchema'
import { commentSchema } from '@/schemas/comments/commentSchema'

type Props = {
  onSubmitComment?: (comment: CommentData) => Promise<void> | void
}

export default function CommentInput({ onSubmitComment }: Props) {
  const [isCommentInputActive, setIsCommentInputActive] = useState(false)

  const {
    register,
    handleSubmit,
    reset,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<CommentData>({
    resolver: zodResolver(commentSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
    defaultValues: {
      content: '',
    },
  })

  const content = watch('content') ?? ''

  function handleCancelComment() {
    reset({ content: '' })
    setIsCommentInputActive(false)
  }

  async function handleSubmitComment(data: CommentData) {
    try {
      await onSubmitComment?.(data)
      reset({ content: '' })
      setIsCommentInputActive(false)
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao enviar seu comentário. Por favor, tente novamente.',
        { id: 'comment-submit-error' }
      )
    }
  }

  return (
    <form onSubmit={handleSubmit(handleSubmitComment)}>
      <label htmlFor="new-comment" className="text-sm font-medium text-white">
        Novo comentario
      </label>

      <input
        id="new-comment"
        type="text"
        onFocus={() => setIsCommentInputActive(true)}
        {...register('content')}
        className="focus:border-cyan/40 mt-3 h-11 w-full rounded-xl border border-white/10 bg-[#0C0424] px-3 text-sm text-white outline-none placeholder:text-white/35"
        placeholder="Escreva um comentario aqui..."
      />

      {errors.content?.message && (
        <p role="alert" className="mt-2 text-sm text-red-500">
          {errors.content?.message}
        </p>
      )}

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
            type="submit"
            disabled={!content.trim() || isSubmitting}
            className="border-cyan/30 bg-cyan/10 text-cyan hover:bg-cyan/20 rounded-xl border px-4 py-2 text-sm font-medium transition disabled:cursor-not-allowed disabled:opacity-50"
          >
            {isSubmitting ? <LoaderCircle className="animate-spin" size={16} /> : 'Comentar'}
          </button>
        </div>
      )}
    </form>
  )
}
