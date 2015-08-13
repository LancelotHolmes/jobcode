#COMAKE2 edit-mode: -*- Makefile -*-
####################64Bit Mode####################
ifeq ($(shell uname -m),x86_64)
CC=gcc
CXX=g++
CXXFLAGS=-g \
  -pipe \
  -W \
  -Wall
CFLAGS=-g \
  -pipe \
  -W \
  -Wall
CPPFLAGS=-D_GNU_SOURCE \
  -D__STDC_LIMIT_MACROS \
  -DUBFLAG_CVSTAG="\"`svn \
  info \
  2>/dev/null|grep \
  "Last \
  Changed \
  Rev"`\"" \
  -DUBFLAG_BUILD_DATE="\"`date \
  +'%Y/%m/%d \
  %H:%M:%S'`\"" \
  -DUBFLAG_PROJECT="\"echo_client\"" \
  -DUBFLAG_VERSION="\"1.0.0.0\""
INCPATH=-I. \
  -I./include
DEP_INCPATH=-I../../../../../third-64/boost \
  -I../../../../../third-64/boost/include \
  -I../../../../../third-64/boost/output \
  -I../../../../../third-64/boost/output/include \
  -I../../../../../third-64/gtest \
  -I../../../../../third-64/gtest/include \
  -I../../../../../third-64/gtest/output \
  -I../../../../../third-64/gtest/output/include

#============ CCP vars ============
CCHECK=@ccheck.py
CCHECK_FLAGS=
PCLINT=@pclint
PCLINT_FLAGS=
CCP=@ccp.py
CCP_FLAGS=


#COMAKE UUID
COMAKE_MD5=03a2dad9b57d4b845105c131ed37da22  COMAKE


.PHONY:all
all:comake2_makefile_check wordparser wordparser_test 
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mall[0m']"
	@echo "make all done"

.PHONY:comake2_makefile_check
comake2_makefile_check:
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mcomake2_makefile_check[0m']"
	#in case of error, update 'Makefile' by 'comake2'
	@echo "$(COMAKE_MD5)">comake2.md5
	@md5sum -c --status comake2.md5
	@rm -f comake2.md5

.PHONY:ccpclean
ccpclean:
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mccpclean[0m']"
	@echo "make ccpclean done"

.PHONY:clean
clean:ccpclean
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mclean[0m']"
	rm -rf wordparser
	rm -rf ./output/bin/wordparser
	rm -rf wordparser_test
	rm -rf ./output/bin/wordparser_test
	rm -rf src/wordparser_custom_type_parser.o
	rm -rf src/wordparser_default_type_parser.o
	rm -rf src/wordparser_default_type_parser_test.o
	rm -rf src/wordparser_dict_parser.o
	rm -rf src/wordparser_main.o
	rm -rf src/wordparser_test_custom_type_parser.o
	rm -rf src/wordparser_test_default_type_parser.o
	rm -rf src/wordparser_test_default_type_parser_test.o
	rm -rf src/wordparser_test_dict_parser.o
	rm -rf src/wordparser_test_main.o
	rm -rf test/wordparser_test_default_type_parser_test.o

.PHONY:dist
dist:
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mdist[0m']"
	tar czvf output.tar.gz output
	@echo "make dist done"

.PHONY:distclean
distclean:clean
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mdistclean[0m']"
	rm -f output.tar.gz
	@echo "make distclean done"

.PHONY:love
love:
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mlove[0m']"
	@echo "make love done"

wordparser:src/wordparser_custom_type_parser.o \
  src/wordparser_default_type_parser.o \
  src/wordparser_default_type_parser_test.o \
  src/wordparser_dict_parser.o \
  src/wordparser_main.o
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mwordparser[0m']"
	$(CXX) src/wordparser_custom_type_parser.o \
  src/wordparser_default_type_parser.o \
  src/wordparser_default_type_parser_test.o \
  src/wordparser_dict_parser.o \
  src/wordparser_main.o -Xlinker "-("  ../../../../../third-64/boost/lib/libboost_atomic.a \
  ../../../../../third-64/boost/lib/libboost_chrono.a \
  ../../../../../third-64/boost/lib/libboost_context.a \
  ../../../../../third-64/boost/lib/libboost_coroutine.a \
  ../../../../../third-64/boost/lib/libboost_date_time.a \
  ../../../../../third-64/boost/lib/libboost_exception.a \
  ../../../../../third-64/boost/lib/libboost_filesystem.a \
  ../../../../../third-64/boost/lib/libboost_graph.a \
  ../../../../../third-64/boost/lib/libboost_locale.a \
  ../../../../../third-64/boost/lib/libboost_math_c99.a \
  ../../../../../third-64/boost/lib/libboost_math_c99f.a \
  ../../../../../third-64/boost/lib/libboost_math_c99l.a \
  ../../../../../third-64/boost/lib/libboost_math_tr1.a \
  ../../../../../third-64/boost/lib/libboost_math_tr1f.a \
  ../../../../../third-64/boost/lib/libboost_math_tr1l.a \
  ../../../../../third-64/boost/lib/libboost_prg_exec_monitor.a \
  ../../../../../third-64/boost/lib/libboost_program_options.a \
  ../../../../../third-64/boost/lib/libboost_python.a \
  ../../../../../third-64/boost/lib/libboost_random.a \
  ../../../../../third-64/boost/lib/libboost_regex.a \
  ../../../../../third-64/boost/lib/libboost_serialization.a \
  ../../../../../third-64/boost/lib/libboost_signals.a \
  ../../../../../third-64/boost/lib/libboost_system.a \
  ../../../../../third-64/boost/lib/libboost_test_exec_monitor.a \
  ../../../../../third-64/boost/lib/libboost_thread.a \
  ../../../../../third-64/boost/lib/libboost_timer.a \
  ../../../../../third-64/boost/lib/libboost_unit_test_framework.a \
  ../../../../../third-64/boost/lib/libboost_wave.a \
  ../../../../../third-64/boost/lib/libboost_wserialization.a \
  ../../../../../third-64/gtest/lib/libgtest.a \
  ../../../../../third-64/gtest/lib/libgtest_main.a -lpthread \
  -lcrypto \
  -lrt -Xlinker "-)" -o wordparser
	mkdir -p ./output/bin
	cp -f --link wordparser ./output/bin

wordparser_test:src/wordparser_test_custom_type_parser.o \
  src/wordparser_test_default_type_parser.o \
  src/wordparser_test_default_type_parser_test.o \
  src/wordparser_test_dict_parser.o \
  src/wordparser_test_main.o \
  test/wordparser_test_default_type_parser_test.o
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mwordparser_test[0m']"
	$(CXX) src/wordparser_test_custom_type_parser.o \
  src/wordparser_test_default_type_parser.o \
  src/wordparser_test_default_type_parser_test.o \
  src/wordparser_test_dict_parser.o \
  src/wordparser_test_main.o \
  test/wordparser_test_default_type_parser_test.o -Xlinker "-("  ../../../../../third-64/boost/lib/libboost_atomic.a \
  ../../../../../third-64/boost/lib/libboost_chrono.a \
  ../../../../../third-64/boost/lib/libboost_context.a \
  ../../../../../third-64/boost/lib/libboost_coroutine.a \
  ../../../../../third-64/boost/lib/libboost_date_time.a \
  ../../../../../third-64/boost/lib/libboost_exception.a \
  ../../../../../third-64/boost/lib/libboost_filesystem.a \
  ../../../../../third-64/boost/lib/libboost_graph.a \
  ../../../../../third-64/boost/lib/libboost_locale.a \
  ../../../../../third-64/boost/lib/libboost_math_c99.a \
  ../../../../../third-64/boost/lib/libboost_math_c99f.a \
  ../../../../../third-64/boost/lib/libboost_math_c99l.a \
  ../../../../../third-64/boost/lib/libboost_math_tr1.a \
  ../../../../../third-64/boost/lib/libboost_math_tr1f.a \
  ../../../../../third-64/boost/lib/libboost_math_tr1l.a \
  ../../../../../third-64/boost/lib/libboost_prg_exec_monitor.a \
  ../../../../../third-64/boost/lib/libboost_program_options.a \
  ../../../../../third-64/boost/lib/libboost_python.a \
  ../../../../../third-64/boost/lib/libboost_random.a \
  ../../../../../third-64/boost/lib/libboost_regex.a \
  ../../../../../third-64/boost/lib/libboost_serialization.a \
  ../../../../../third-64/boost/lib/libboost_signals.a \
  ../../../../../third-64/boost/lib/libboost_system.a \
  ../../../../../third-64/boost/lib/libboost_test_exec_monitor.a \
  ../../../../../third-64/boost/lib/libboost_thread.a \
  ../../../../../third-64/boost/lib/libboost_timer.a \
  ../../../../../third-64/boost/lib/libboost_unit_test_framework.a \
  ../../../../../third-64/boost/lib/libboost_wave.a \
  ../../../../../third-64/boost/lib/libboost_wserialization.a \
  ../../../../../third-64/gtest/lib/libgtest.a \
  ../../../../../third-64/gtest/lib/libgtest_main.a -lpthread \
  -lcrypto \
  -lrt -Xlinker "-)" -o wordparser_test
	mkdir -p ./output/bin
	cp -f --link wordparser_test ./output/bin

src/wordparser_custom_type_parser.o:src/custom_type_parser.cpp \
  include/custom_type_parser.h \
  include/base_type_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_custom_type_parser.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_custom_type_parser.o src/custom_type_parser.cpp

src/wordparser_default_type_parser.o:src/default_type_parser.cpp \
  include/default_type_parser.h \
  include/base_type_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_default_type_parser.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_default_type_parser.o src/default_type_parser.cpp

src/wordparser_default_type_parser_test.o:src/default_type_parser_test.cpp
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_default_type_parser_test.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_default_type_parser_test.o src/default_type_parser_test.cpp

src/wordparser_dict_parser.o:src/dict_parser.cpp \
  include/default_type_parser.h \
  include/base_type_parser.h \
  include/dict_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_dict_parser.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_dict_parser.o src/dict_parser.cpp

src/wordparser_main.o:src/main.cpp \
  include/dict_parser.h \
  include/base_type_parser.h \
  include/custom_type_parser.h \
  include/default_type_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_main.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_main.o src/main.cpp

src/wordparser_test_custom_type_parser.o:src/custom_type_parser.cpp \
  include/custom_type_parser.h \
  include/base_type_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_test_custom_type_parser.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_test_custom_type_parser.o src/custom_type_parser.cpp

src/wordparser_test_default_type_parser.o:src/default_type_parser.cpp \
  include/default_type_parser.h \
  include/base_type_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_test_default_type_parser.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_test_default_type_parser.o src/default_type_parser.cpp

src/wordparser_test_default_type_parser_test.o:src/default_type_parser_test.cpp
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_test_default_type_parser_test.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_test_default_type_parser_test.o src/default_type_parser_test.cpp

src/wordparser_test_dict_parser.o:src/dict_parser.cpp \
  include/default_type_parser.h \
  include/base_type_parser.h \
  include/dict_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_test_dict_parser.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_test_dict_parser.o src/dict_parser.cpp

src/wordparser_test_main.o:src/main.cpp \
  include/dict_parser.h \
  include/base_type_parser.h \
  include/custom_type_parser.h \
  include/default_type_parser.h
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40msrc/wordparser_test_main.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o src/wordparser_test_main.o src/main.cpp

test/wordparser_test_default_type_parser_test.o:test/default_type_parser_test.cpp
	@echo "[[1;32;40mCOMAKE:BUILD[0m][Target:'[1;32;40mtest/wordparser_test_default_type_parser_test.o[0m']"
	$(CXX) -c $(INCPATH) $(DEP_INCPATH) $(CPPFLAGS) $(CXXFLAGS)  -o test/wordparser_test_default_type_parser_test.o test/default_type_parser_test.cpp

endif #ifeq ($(shell uname -m),x86_64)


