import React, { useState, useEffect, useMemo, useRef } from "react";
import { router, useLocalSearchParams } from "expo-router";
import { View, Text, ScrollView, Pressable, Alert, KeyboardAvoidingView, Platform } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import PlayerHeader from "../components/watch/playerHeader";
import LessonList from "../components/watch/lessonList";
import CertificateButton from "../components/watch/certificateButton";
import CommentsList from "../components/watch/commentList";
import CommentsPreview from "../components/watch/commentPreview";
import CommentInput from "../components/watch/commentInput";
import { useLessons } from "../hooks/useLessons";
import { useLessonComments } from "../hooks/useLessonComments";

export default function Watch() {
  const scrollRef = useRef(null);
  const params = useLocalSearchParams();
  const courseID = params.courseID;
  const courseSlug = params.courseSlug;

  const { lessons, loading, error, refetch } = useLessons({ courseNameSlug: courseSlug });

  const [commentInputY, setCommentInputY] = useState(0);
  const [currentLesson, setCurrentLesson] = useState(lessons[0]);
  const [watched, setWatched] = useState(new Set());
  const [tab, setTab] = useState("lessons");

  const { comments, refetch: refetchComments } = useLessonComments(currentLesson ? { lessonId: currentLesson.id } : {});


  const [commentsMap, setCommentsMap] = useState({});


  useEffect(() => {
    if (lessons.length > 0 && !currentLesson) {
      setCurrentLesson(lessons[0]);
    }
  }, [lessons]);

  useEffect(() => {
    if (!comments || !currentLesson) return;

    setCommentsMap((prev) => ({
      ...prev,
      [currentLesson.id]: comments,
    }));
  }, [comments, currentLesson]);

  const watchedCount = watched.size;
  const allWatched = watchedCount === lessons.length;

  const scrollToComment = () => {
    requestAnimationFrame(() => {
      scrollRef.current?.scrollTo({
        y: Math.max(0, commentInputY - 80),
        animated: true,
      });
    });
  };

  const handleToggleWatched = (lessonId) => {
    setWatched((prev) => {
      const next = new Set(prev);
      if (next.has(lessonId)) next.delete(lessonId);
      else next.add(lessonId);
      return next;
    });
  };

  const handleGenerateCertificate = () => {
    if (!allWatched) {
      Alert.alert("Atenção", "Você precisa assistir todas as aulas para gerar o certificado.");
      return;
    }
    Alert.alert("Sucesso", "Certificado gerado.");
  };

  const progressPercent = useMemo(() => {
    return Math.round((watchedCount / lessons.length) * 100);
  }, [watchedCount, lessons.length]);

  const currentComments = commentsMap[currentLesson?.id] ?? [];
  const latest = currentComments[0] || null;

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "position"}
      keyboardVerticalOffset={Platform.OS === "android" ? 80 : -20}
    >
      <View className="flex-1 bg-custom-primary">

        <View className="px-6 pt-4 pb-4 flex-row items-center justify-between">
          <View className="flex-row items-center">
            <Pressable onPress={() => router.push("(tabs)/myCourses")} className="mr-3">
              <Ionicons name="arrow-back-outline" size={26} color="white" />
            </Pressable>
            <Text className="text-white text-3xl font-bold">Assistir Aula</Text>
          </View>
          <Ionicons name="play-circle-outline" size={32} color="white" />
        </View>

        <ScrollView
          ref={scrollRef}
          className="flex-1 px-4"
          keyboardShouldPersistTaps="handled"
          showsVerticalScrollIndicator={false}
        >
          {/* player do youtube */}

          {currentLesson && (
            <PlayerHeader
              youtubeId={currentLesson.youtubeLink}
              title={currentLesson.title}
              description={currentLesson.description}
            />
          )}

          {/* abas de aulas e comentários */}
          <View className="mt-2 px-2 flex-row gap-2">
            <Pressable
              onPress={() => setTab("lessons")}
              className={`px-4 py-2 rounded-full ${tab === "lessons" ? "bg-white" : "bg-white/10"}`}
            >
              <Text className={`${tab === "lessons" ? "text-black" : "text-white"}`}>Aulas</Text>
            </Pressable>

            <Pressable
              onPress={() => setTab("comments")}
              className={`px-4 py-2 rounded-full flex-row items-center gap-2 ${tab === "comments" ? "bg-white" : "bg-white/10"}`}
            >
              <Text className={`${tab === "comments" ? "text-black" : "text-white"}`}>Comentários</Text>
              <View className={`px-2 py-0.5 rounded-full ${tab === "comments" ? "bg-black/10" : "bg-white/20"}`}>
                <Text className={`${tab === "comments" ? "text-black/80" : "text-white/90"} text-xs`}>{currentComments.length}</Text>
              </View>
            </Pressable>
          </View>

          {/* preview dos comentários */}
          <CommentsPreview
            latest={latest}
            count={currentComments.length}
            onPress={() => setTab("comments")}
          />

          {tab === "lessons" && (
            <View className="mt-4 px-2">
              <Text className="text-white font-medium mb-2">
                Progresso: {watchedCount} / {lessons.length}
              </Text>
              <View className="w-full h-3 bg-white/10 rounded-full overflow-hidden">
                <View className="h-full bg-white" style={{ width: `${progressPercent}%` }} />
              </View>
            </View>
          )}

          {/* lista de aulas ou comentários */}
          <View className="mt-6">
            {tab === "lessons" ? (
              <>
                <Text className="text-white text-xl font-bold mb-3">Aulas do Curso</Text>
                <LessonList
                  lessons={lessons}
                  currentId={currentLesson?.id}
                  onSelect={(lesson) => {
                    setCurrentLesson(lesson);
                    setTab("lessons");
                  }}
                  watchedSet={watched}
                  onToggleWatched={handleToggleWatched}
                />
              </>
            ) : (
              <>
                <Text className="text-white text-xl font-bold mb-3">Comentários</Text>
                <CommentsList comments={currentComments} />
              </>
            )}
          </View>

          {tab === "lessons" && (
            <View className="px-2 mt-6 mb-8">
              <CertificateButton disabled={!allWatched} onPress={handleGenerateCertificate} />
            </View>
          )}

          {tab === "comments" && (
            <View onLayout={(e) => setCommentInputY(e.nativeEvent.layout.y)}>
              <CommentInput
                lessonId={currentLesson?.id}
                parentId={null}
                onSuccess={async () => {
                  await refetchComments();
                  scrollToComment();
                }}
              />
            </View>
          )}

        </ScrollView>
      </View>
    </KeyboardAvoidingView>
  );
}